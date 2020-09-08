import { Moment } from 'moment';
import { IProducts } from 'app/shared/model/products.model';

export interface IOrders {
  id?: number;
  dateCreated?: Moment;
  status?: string;
  products?: IProducts[];
}

export class Orders implements IOrders {
  constructor(public id?: number, public dateCreated?: Moment, public status?: string, public products?: IProducts[]) {}
}
