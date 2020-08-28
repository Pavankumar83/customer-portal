import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IPerson {
  id?: number;
  firstName?: string;
  lastName?: string;
  title?: string;
  gender?: Gender;
  dateOfBirth?: Moment;
  deleted?: boolean;
  customer?: ICustomer;
}

export class Person implements IPerson {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public title?: string,
    public gender?: Gender,
    public dateOfBirth?: Moment,
    public deleted?: boolean,
    public customer?: ICustomer
  ) {
    this.deleted = this.deleted || false;
  }
}
