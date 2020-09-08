import { IAvailableTransaction } from 'app/shared/model/available-transaction.model';
import { IInstitution } from 'app/shared/model/institution.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IBankInfo {
  id?: number;
  name?: string;
  accountNumber?: string;
  accountHolder?: string;
  branchCode?: string;
  branchAddress?: string;
  ifscCode?: string;
  availableTransactions?: IAvailableTransaction[];
  institution?: IInstitution;
  customer?: ICustomer;
}

export class BankInfo implements IBankInfo {
  constructor(
    public id?: number,
    public name?: string,
    public accountNumber?: string,
    public accountHolder?: string,
    public branchCode?: string,
    public branchAddress?: string,
    public ifscCode?: string,
    public availableTransactions?: IAvailableTransaction[],
    public institution?: IInstitution,
    public customer?: ICustomer
  ) {}
}
