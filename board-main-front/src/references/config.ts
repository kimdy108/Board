const apiUrl = window.location.hostname
const apiMethod = window.location.protocol

const apiPort = process.env.VUE_APP_API_PORT

export const baseMethod = apiMethod
export const baseAddr = apiUrl
export const basePort = apiPort

export const baseUrl = `${apiMethod}//${apiUrl}:${apiPort}/api`

export const emptyUUID = '00000000-0000-0000-0000-000000000000'
export const divisionChar = '🄱🄾🄰🅁🄳'  // 🄰🄱🄲🄳🄴🄵🄶🄷🄸🄹🄺🄻🄼🄽🄾🄿🅀🅁🅂🅃🅄🅅🅆🅇🅈🅉
export const defineAuthKey = 'zb7vhcQZ6QUTwKhKtj8SPmUrXkg1THNQ'

export const idReg            = /^[A-Za-z0-9]{4,20}$/
export const nameReg          = /^[가-힣a-zA-Z0-9. ()<>-]{2,20}$/
export const phoneReg         = /^01([0|1|6|7|8|9])([0-9]{3,4})([0-9]{4})$/
export const passwdReg        = /^(?=.*[a-z])(?=.*\d)(?=.*[~!@#$%^&*()])[A-Za-z\d~!@#$%^&*()]{8,}$/

export const userRoleList = [
    { key: 0, name: '최고관리자', value: 'MASTER' },
    { key: 1, name: '관리자', value: 'ADMIN' },
    { key: 2, name: '매니저', value: 'MANAGER' },
    { key: 3, name: '사용자', value: 'MEMBER' }
]