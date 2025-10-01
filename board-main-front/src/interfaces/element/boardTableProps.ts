export default interface boardTableProps {
  totalCount: number,
  contents: any[],
  columnHeader: {
    seq: number,
    field: string,
    header: string,
    style: string
  }[],
  isUpdate: boolean,
  isDelete: boolean,
  keyName: string
}