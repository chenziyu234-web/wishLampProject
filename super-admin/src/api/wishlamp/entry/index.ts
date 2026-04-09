import request from '@/utils/request';
import type { WishEntryQuery, WishEntryVO } from './types';
import type { AxiosPromise } from 'axios';

export function listEntry(query: WishEntryQuery): AxiosPromise<WishEntryVO[]> {
  return request({
    url: '/wishlamp/entry/list',
    method: 'get',
    params: query,
  });
}

export function delEntry(entryId: string | number | Array<string | number>) {
  return request({
    url: '/wishlamp/entry/' + entryId,
    method: 'delete',
  });
}
