import { Moment } from 'moment';
import { IInstitution } from 'app/shared/model/institution.model';

export interface INonWorkingDay {
  id?: number;
  nonWorkingDay?: Moment;
  deleted?: boolean;
  institution?: IInstitution;
}

export class NonWorkingDay implements INonWorkingDay {
  constructor(public id?: number, public nonWorkingDay?: Moment, public deleted?: boolean, public institution?: IInstitution) {
    this.deleted = this.deleted || false;
  }
}
