export interface WishInstanceVO extends BaseEntity {
  instanceId: number | string;
  productId: number | string;
  name: string;
  status: string;
  config: string;
  startTime: string;
  endTime: string;
  productName: string;
  productType: string;
}

export interface WishInstanceForm {
  instanceId: number | string | undefined;
  productId: number | string | undefined;
  name: string;
  status: string;
  config: string;
  startTime: string;
  endTime: string;
}

export interface WishInstanceQuery extends PageQuery {
  name: string;
  status: string;
  productId: number | string | undefined;
}

export interface WishStats {
  totalInstances: number;
  activeInstances: number;
  totalProducts: number;
}
