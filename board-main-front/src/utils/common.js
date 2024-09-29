import CryptoJS from 'crypto-js'

const defineAuthKey = 'bz9Tkpw5cz2XnuTDBgj6biqw7WjNW5De'
const difineAuthIv = 'KIMDY@STUDYADMIN'

export const encryptString = (str) => {
  const key = btoa(defineAuthKey)
  const iv = CryptoJS.enc.Hex.parse(hexEncode(difineAuthIv))

  const encrypt = CryptoJS.AES.encrypt(str, CryptoJS.enc.Base64.parse(key), {
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.Pkcs7,
    format: CryptoJS.format.OpenSSL,
    iv: iv
  }).toString()

  return encrypt
}

export const decryptString = (str) => {
  const key = btoa(defineAuthKey)
  const iv = CryptoJS.enc.Hex.parse(hexEncode(difineAuthIv))

  const decrypt = CryptoJS.AES.decrypt(str, CryptoJS.enc.Base64.parse(key), {
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.Pkcs7,
    format: CryptoJS.format.OpenSSL,
    iv: iv
  })

  return decrypt.toString(CryptoJS.enc.Utf8)
}

export const hexEncode = (str) => {
  let hex, i
  let result = ''
  for (i = 0; i < str.length; i++) {
    hex = str.charCodeAt(i).toString(16)
    result += ('' + hex).slice(-4)
  }
  return result
}
