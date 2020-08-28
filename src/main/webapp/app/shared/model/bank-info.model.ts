import { ICustomer } from 'app/shared/model/customer.model';

export interface IBankInfo {
  id?: number;
  name?: string;
  accountHolder?: string;
  accountNumber?: string;
  branchCode?: string;
  branchAddress?: string;
  ifscCode?: string;
  customer?: ICustomer;
}

export class BankInfo implements IBankInfo {
  constructor(
    public id?: number,
    public name?: string,
    public accountHolder?: string,
    public accountNumber?: string,
    public branchCode?: string,
    public branchAddress?: string,
    public ifscCode?: string,
    public customer?: ICustomer
  ) {}
}
