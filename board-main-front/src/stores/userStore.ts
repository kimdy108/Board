import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

import type userStorage from '@/interfaces/store/userStorage'

export const useUserStore = defineStore('user', () => {
  const currentUser = ref({ at: null, rt: null, uid: null, unm: null, uud: null, uro: null, sud: null })
  const userRole = ref('')
  
  const getCurrentUser = computed(() => {
    const sessionStored = sessionStorage.getItem('user') || '{}'
    const sessionObj = JSON.parse(sessionStored)
  
    if (currentUser.value.at == null && sessionObj.at != null) setCurrentUser(sessionObj)
    return currentUser.value ? currentUser.value : JSON.parse(sessionStored)
  })
  const getUserRole = computed(() => {
    const sessionStored = sessionStorage.getItem('user') || '{}'
    const sessionObj = JSON.parse(sessionStored)
  
    if (userRole.value == '' && sessionObj.uro != null) setUserRole(sessionObj.uro)
    return userRole.value ? userRole.value : sessionObj.uro
  })
  
  function setCurrentUser(newCurrentUser: userStorage) {
    sessionStorage.setItem('user', JSON.stringify(newCurrentUser))
    currentUser.value = newCurrentUser
  }
  function setUserRole(newUserRole: string) {
    userRole.value = newUserRole
  }
  function setUserLogout() {
    sessionStorage.removeItem('user')
    currentUser.value = { at: null, rt: null, uid: null, unm: null, uud: null, uro: null, sud: null }
  }
  
  return {
    getCurrentUser,
    getUserRole,
    setCurrentUser,
    setUserRole,
    setUserLogout
  }
})