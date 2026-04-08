export interface WishProductVO extends BaseEntity {
  productId: number | string;
  name: string;
  slug: string;
  type: string;
  description: string;
  iconUrl: string;
  enabled: number;
}

export interface WishProductForm {
  productId: number | string | undefined;
  name: string;
  slug: string;
  type: string;
  description: string;
  iconUrl: string;
  enabled: number;
}

export interface WishProductQuery extends PageQuery {
  name: string;
  type: string;
  slug: string;
  enabled: number | undefined;
}
