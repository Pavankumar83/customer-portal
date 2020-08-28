import { ICustomer } from 'app/shared/model/customer.model';

export interface IInstitution {
  id?: number;
  name?: string;
  tradeName?: string;
  taxNumber?: string;
  url?: string;
  deleted?: boolean;
  customers?: ICustomer[];
}

export class Institution implements IInstitution {
  constructor(
    public id?: number,
    public name?: string,
    public tradeName?: string,
    public taxNumber?: string,
    public url?: string,
    public deleted?: boolean,
    public customers?: ICustomer[]
  ) {
    this.deleted = this.deleted || false;
  }
}
