import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';

export interface ILegalEntity {
  id?: number;
  commercialName?: string;
  taxNumber?: string;
  title?: string;
  dateOfStart?: Moment;
  businessClosed?: boolean;
  businessArea?: string;
  deleted?: boolean;
  customer?: ICustomer;
}

export class LegalEntity implements ILegalEntity {
  constructor(
    public id?: number,
    public commercialName?: string,
    public taxNumber?: string,
    public title?: string,
    public dateOfStart?: Moment,
    public businessClosed?: boolean,
    public businessArea?: string,
    public deleted?: boolean,
    public customer?: ICustomer
  ) {
    this.businessClosed = this.businessClosed || false;
    this.deleted = this.deleted || false;
  }
}
