import { ICustomerEmail } from 'app/shared/model/customer-email.model';
import { ICustomerPhone } from 'app/shared/model/customer-phone.model';
import { ICustomerAddress } from 'app/shared/model/customer-address.model';
import { IBankInfo } from 'app/shared/model/bank-info.model';
import { IAvailableTransaction } from 'app/shared/model/available-transaction.model';
import { IReport } from 'app/shared/model/report.model';
import { IdentificationType } from 'app/shared/model/enumerations/identification-type.model';
import { CustomerType } from 'app/shared/model/enumerations/customer-type.model';

export interface ICustomer {
  id?: number;
  name?: string;
  identificationType?: IdentificationType;
  customerType?: CustomerType;
  deleted?: boolean;
  customerEmails?: ICustomerEmail[];
  customerPhones?: ICustomerPhone[];
  customerAddresses?: ICustomerAddress[];
  bankInfos?: IBankInfo[];
  availableTransactions?: IAvailableTransaction[];
  reports?: IReport[];
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public name?: string,
    public identificationType?: IdentificationType,
    public customerType?: CustomerType,
    public deleted?: boolean,
    public customerEmails?: ICustomerEmail[],
    public customerPhones?: ICustomerPhone[],
    public customerAddresses?: ICustomerAddress[],
    public bankInfos?: IBankInfo[],
    public availableTransactions?: IAvailableTransaction[],
    public reports?: IReport[]
  ) {
    this.deleted = this.deleted || false;
  }
}
