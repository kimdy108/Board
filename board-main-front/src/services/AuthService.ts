import axios from 'axios'
import type requestData from '@/interfaces/common/requestData'
import { baseUrl } from '@/references/config'

////////////////////////// REQUEST //////////////////////////
axios.interceptors.request.use(async function (config) {
    return config;
  },
  function (error) {
    return Promise.reject(error);
  });

  ////////////////////////// RESPONSE //////////////////////////
axios.interceptors.response.use(function (response) {
    return response;
  },
  async function (error) {
    return Promise.reject(error);
  });
  
  class AuthService {
    async requestLogin (req: any) {
      const reqObj: requestData = {} as requestData
      reqObj.baseURL = baseUrl
      reqObj.url = '/auth/login'
      reqObj.method = 'POST'
      reqObj.headers = { accept: 'application/json' }
  
      if (req.data) reqObj.data = req.data
      try {
        const result: any = await axios(reqObj)
        result.data.retData = {enc: result.data.retData, at: result.headers['accesstoken'], rt:result.headers['refreshtoken']}
        return result.data
      }
      catch (error: any) {
        if(!error.response) return error
        else return error?.response?.data
      }
    }
  }
  
  export default new AuthService()
  