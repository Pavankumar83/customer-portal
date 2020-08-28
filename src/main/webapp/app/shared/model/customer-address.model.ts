import { ICustomer } from 'app/shared/model/customer.model';

export interface ICustomerAddress {
  id?: number;
  address?: string;
  town?: string;
  city?: string;
  zipCode?: string;
  state?: string;
  country?: string;
  deleted?: boolean;
  customer?: ICustomer;
}

export class CustomerAddress implements ICustomerAddress {
  constructor(
    public id?: number,
    public address?: string,
    public town?: string,
    public city?: string,
    public zipCode?: string,
    public state?: string,
    public country?: string,
    public deleted?: boolean,
    public customer?: ICustomer
  ) {
    this.deleted = this.deleted || false;
  }
}
