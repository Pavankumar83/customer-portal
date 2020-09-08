import { IBankInfo } from 'app/shared/model/bank-info.model';
import { INonWorkingDay } from 'app/shared/model/non-working-day.model';

export interface IInstitution {
  id?: number;
  name?: string;
  tradeName?: string;
  taxNumber?: string;
  url?: string;
  deleted?: boolean;
  bankInfos?: IBankInfo[];
  nonWorkingDays?: INonWorkingDay[];
}

export class Institution implements IInstitution {
  constructor(
    public id?: number,
    public name?: string,
    public tradeName?: string,
    public taxNumber?: string,
    public url?: string,
    public deleted?: boolean,
    public bankInfos?: IBankInfo[],
    public nonWorkingDays?: INonWorkingDay[]
  ) {
    this.deleted = this.deleted || false;
  }
}
