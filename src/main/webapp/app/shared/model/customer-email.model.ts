import { ICustomer } from 'app/shared/model/customer.model';

export interface ICustomerEmail {
  id?: number;
  email?: string;
  deleted?: boolean;
  customer?: ICustomer;
}

export class CustomerEmail implements ICustomerEmail {
  constructor(public id?: number, public email?: string, public deleted?: boolean, public customer?: ICustomer) {
    this.deleted = this.deleted || false;
  }
}
