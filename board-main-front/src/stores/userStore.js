import { defineStore } from 'pinia'
import { computed, ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const userAccess = ref({ at: null, rt: null, uid: null, ugd: null, rol: null })
  const userRole = ref('')

  const getUserAccess = computed(() => {
    const sessionStored = sessionStorage.getItem('user') || '{}'
    const sessionObj = JSON.parse(sessionStored)

    if (userAccess.value.at == null && sessionObj.at != null) setUserAccess(sessionObj)
    return userAccess.value ? userAccess.value : JSON.parse(sessionStored)
  })

  const getUserRole = computed(() => {
    const sessionStored = sessionStorage.getItem('user') || '{}'
    const sessionObj = JSON.parse(sessionStored)

    if (userRole.value == '' && sessionObj.rol != null) setUserRole(sessionObj.uro)
    return userRole.value ? userRole.value : sessionObj.rol
  })

  function setUserAccess(newUserAccess) {
    sessionStorage.setItem('user', JSON.stringify(newUserAccess))
    userAccess.value = newUserAccess
  }
  function setUserRole(newUserRole) {
    userRole.value = newUserRole
  }
  function setUserLogout() {
    sessionStorage.removeItem('user')
    userAccess.value = { at: null, rt: null, uid: null, ugd: null, rol: null }
  }

  return {
    getUserAccess,
    getUserRole,
    setUserAccess,
    setUserRole,
    setUserLogout
  }
})
