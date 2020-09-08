import { IOrders } from 'app/shared/model/orders.model';

export interface IProducts {
  id?: number;
  name?: string;
  price?: number;
  pictureUrl?: string;
  orders?: IOrders;
}

export class Products implements IProducts {
  constructor(public id?: number, public name?: string, public price?: number, public pictureUrl?: string, public orders?: IOrders) {}
}
