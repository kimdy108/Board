import axios from 'axios'
import { useUserStore } from '@/stores/userStore'
import { encryptString } from '@/utils/common'
import { baseUrl } from '@/reference/config'

////////////////////////// REQUEST //////////////////////////
axios.interceptors.request.use(
  async function (config) {
    const userStore = useUserStore()

    if (userStore.getUserAccess != null && userStore.getUserAccess.at != null) {
      config.headers['Authorization'] = `Bearer ${userStore.getUserAccess.at}`
      config.headers['uid'] = `${userStore.getUserAccess.uid}`
      config.headers['ugd'] = `${userStore.getUserAccess.ugd}`
    }

    return config
  },
  function (error) {
    return Promise.reject(error)
  }
)

////////////////////////// RESPONSE //////////////////////////
axios.interceptors.response.use(
  function (response) {
    return response
  },
  async function (error) {
    if (error === 'accessTokenError') {
      const userStore = useUserStore()

      const refreshResult = await authRefreshRequest()
      if (refreshResult.outPut) {
        const item = userStore.getCurrentUser
        item.at = refreshResult.accessToken
        userStore.setCurrentUser(item)

        return axios(error.config)
      } else {
        return error
      }
    }

    return Promise.reject(error)
  }
)

class ApiService {
  async requestAPI(req) {
    const reqObj = {}
    reqObj.baseURL = baseUrl
    reqObj.url = req.url
    reqObj.method = req.method
    reqObj.headers = req.headers

    if (req.params) reqObj.params = req.params
    if (req.data) reqObj.data = req.data
    if (req.headers) reqObj.headers = req.headers
    if (req.responseType) reqObj.responseType = req.responseType
    try {
      const result = await axios(reqObj)
      return result.data
    } catch (error) {
      return error
    }
  }
}

const authRefreshRequest = async () => {
  const userStore = useUserStore()

  const reqObj = {}
  reqObj.baseURL = baseUrl
  reqObj.url = '/member/access/token/refresh'
  reqObj.method = 'POST'

  reqObj.data = {
    id: encryptString(userStore.getCurrentUser.uid),
    refreshToken: userStore.getCurrentUser.rt
  }
  reqObj.headers = { accept: 'application/json' }
  try {
    const result = await axios(reqObj)
    return result.data
  } catch (error) {
    return error.data
  }
}

export default new ApiService()
