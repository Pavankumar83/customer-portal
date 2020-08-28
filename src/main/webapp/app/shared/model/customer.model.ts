import { ICustomerEmail } from 'app/shared/model/customer-email.model';
import { ICustomerPhone } from 'app/shared/model/customer-phone.model';
import { ICustomerAddress } from 'app/shared/model/customer-address.model';
import { IBankInfo } from 'app/shared/model/bank-info.model';
import { IInstitution } from 'app/shared/model/institution.model';
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
  institution?: IInstitution;
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
    public institution?: IInstitution
  ) {
    this.deleted = this.deleted || false;
  }
}
