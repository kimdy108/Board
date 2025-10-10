import { divisionChar, defineAuthKey } from '@/references/config'

import CryptoJS from 'crypto-js'

export const encryptString = (str: string) => {
  if (str == null) return ''

  const key = btoa(defineAuthKey)
  const iv = CryptoJS.enc.Hex.parse(hexEncode('KIMDY@BOARDADMIN'))

  const encrypt = CryptoJS.AES.encrypt(str, CryptoJS.enc.Base64.parse(key), {
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.Pkcs7,
    format: CryptoJS.format.OpenSSL,
    iv: iv
  }).toString()

  return encrypt
}

export const decryptString = (str: string) => {
  if (str == null) return ''

  const key = btoa(defineAuthKey)
  const iv = CryptoJS.enc.Hex.parse(hexEncode('KIMDY@BOARDADMIN'))

  const decrypt = CryptoJS.AES.decrypt(str, CryptoJS.enc.Base64.parse(key), {
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.Pkcs7,
    format: CryptoJS.format.OpenSSL,
    iv: iv
  })

  return decrypt.toString(CryptoJS.enc.Utf8)
}

export const hexEncode = (str: string) => {
  let hex, i;
  let result = "";
  for (i=0; i<str.length; i++) {
    hex = str.charCodeAt(i).toString(16);
    result += (""+hex).slice(-4);
  }
  return result
}

export const rndStr = (len: number) => {
  let text = ""
  const chars = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLNMOPQRSTUVWXYZ"
  for( let i=0; i < len; i++ ) {
    text += chars.charAt(Math.floor(Math.random() * chars.length))
  }
  return text;
}

export const encryptStringSalt = (str: string) => {
  const encFinalStr = rndStr(8) + divisionChar + str + divisionChar + rndStr(8)
  return encryptString(encFinalStr)
}

export const decryptStringSalt = (str: string) => {
  const decFinalStr = decryptString(str)
  return decFinalStr.split(divisionChar)[1]
}