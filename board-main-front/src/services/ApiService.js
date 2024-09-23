import axios from 'axios'

const baseUrl = `${import.meta.env.VITE_APP_API_URL}/api`

class ApiService {
  async requestAPI(req) {
    const reqObj = {}
    reqObj.baseURL = baseUrl
    reqObj.url = req.url
    reqObj.method = req.method

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

export default new ApiService()
