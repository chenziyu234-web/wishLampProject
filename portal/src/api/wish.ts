import axios from 'axios'

const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  timeout: 10000,
})

export interface WishEntryForm {
  instanceId: number
  participantName?: string
  toName?: string
  message?: string
  cardStyle?: string
}

export async function submitWishEntry(data: WishEntryForm): Promise<{ code: number; msg: string; data: number }> {
  const res = await request.post('/public/wish/entry', data)
  return res.data
}
