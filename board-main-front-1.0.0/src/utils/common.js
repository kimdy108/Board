import CryptoJS from 'crypto-js'

const defineAuthKey = 'bz9Tkpw5cz2XnuTDBgj6biqw7WjNW5De'
const difineAuthIv = 'KIMDY@STUDYADMIN'
const AUTHCHAR = '🄱🄾🄰🅁🄳'

export const rndStr = (len) => {
  let text = ''
  const chars = 'abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLNMOPQRSTUVWXYZ'
  for (let i = 0; i < len; i++) {
    text += chars.charAt(Math.floor(Math.random() * chars.length))
  }
  return text
}

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

export const encryptStringSalt = (str) => {
  const encFinalStr = rndStr(10) + AUTHCHAR + str + AUTHCHAR + rndStr(10)
  return encryptString(encFinalStr)
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

export const decryptStringSalt = (str) => {
  const decFinalStr = decryptString(str)
  return decFinalStr.split(AUTHCHAR)[1]
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
