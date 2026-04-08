import request from '@/utils/request';
import { WishProductForm, WishProductQuery, WishProductVO } from './types';
import { AxiosPromise } from 'axios';

export function listProduct(query: WishProductQuery): AxiosPromise<WishProductVO[]> {
  return request({
    url: '/wishlamp/product/list',
    method: 'get',
    params: query
  });
}

export function listAllProduct(): AxiosPromise<WishProductVO[]> {
  return request({
    url: '/wishlamp/product/listAll',
    method: 'get'
  });
}

export function getProduct(productId: string | number): AxiosPromise<WishProductVO> {
  return request({
    url: '/wishlamp/product/' + productId,
    method: 'get'
  });
}

export function addProduct(data: WishProductForm) {
  return request({
    url: '/wishlamp/product',
    method: 'post',
    data: data
  });
}

export function updateProduct(data: WishProductForm) {
  return request({
    url: '/wishlamp/product',
    method: 'put',
    data: data
  });
}

export function delProduct(productId: string | number | Array<string | number>) {
  return request({
    url: '/wishlamp/product/' + productId,
    method: 'delete'
  });
}
