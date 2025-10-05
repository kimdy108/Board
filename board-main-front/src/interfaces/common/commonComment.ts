export default interface commonComment {
  commentUUID: string
  memberUUID: string
  memberNickName: string
  commentContent: string
  insertDate: string
  updateDate: string
  isEncrypt: 'YES' | 'NO'
}