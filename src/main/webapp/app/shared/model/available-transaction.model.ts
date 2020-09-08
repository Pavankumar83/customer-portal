import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';
import { IBankInfo } from 'app/shared/model/bank-info.model';
import { TransactionType } from 'app/shared/model/enumerations/transaction-type.model';
import { TransactionMode } from 'app/shared/model/enumerations/transaction-mode.model';

export interface IAvailableTransaction {
  id?: number;
  transactionId?: string;
  transactionType?: TransactionType;
  transactionMode?: TransactionMode;
  transAmount?: number;
  dateOfTransaction?: Moment;
  customer?: ICustomer;
  account?: IBankInfo;
}

export class AvailableTransaction implements IAvailableTransaction {
  constructor(
    public id?: number,
    public transactionId?: string,
    public transactionType?: TransactionType,
    public transactionMode?: TransactionMode,
    public transAmount?: number,
    public dateOfTransaction?: Moment,
    public customer?: ICustomer,
    public account?: IBankInfo
  ) {}
}
