import request from '@/utils/request';
import { WishInstanceForm, WishInstanceQuery, WishInstanceVO, WishStats } from './types';
import { AxiosPromise } from 'axios';

export function listInstance(query: WishInstanceQuery): AxiosPromise<WishInstanceVO[]> {
  return request({
    url: '/wishlamp/instance/list',
    method: 'get',
    params: query
  });
}

export function getInstance(instanceId: string | number): AxiosPromise<WishInstanceVO> {
  return request({
    url: '/wishlamp/instance/' + instanceId,
    method: 'get'
  });
}

export function addInstance(data: WishInstanceForm) {
  return request({
    url: '/wishlamp/instance',
    method: 'post',
    data: data
  });
}

export function updateInstance(data: WishInstanceForm) {
  return request({
    url: '/wishlamp/instance',
    method: 'put',
    data: data
  });
}

export function delInstance(instanceId: string | number | Array<string | number>) {
  return request({
    url: '/wishlamp/instance/' + instanceId,
    method: 'delete'
  });
}

export function getStats(): AxiosPromise<WishStats> {
  return request({
    url: '/wishlamp/instance/stats',
    method: 'get'
  });
}
