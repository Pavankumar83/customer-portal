import { ICustomer } from 'app/shared/model/customer.model';

export interface ICustomerPhone {
  id?: number;
  phoneNumber?: string;
  extension?: string;
  deleted?: boolean;
  customer?: ICustomer;
}

export class CustomerPhone implements ICustomerPhone {
  constructor(
    public id?: number,
    public phoneNumber?: string,
    public extension?: string,
    public deleted?: boolean,
    public customer?: ICustomer
  ) {
    this.deleted = this.deleted || false;
  }
}
