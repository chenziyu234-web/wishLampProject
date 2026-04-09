export interface WishEntryVO {
  entryId: number | string
  instanceId: number | string
  participantName: string
  toName: string
  message: string
  cardStyle: string
  ipAddress: string
  createTime: string
  instanceName: string
}

export interface WishEntryQuery extends PageQuery {
  instanceId: number | string | undefined
  participantName: string
}
