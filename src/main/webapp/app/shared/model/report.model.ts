import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';
import { ReportType } from 'app/shared/model/enumerations/report-type.model';

export interface IReport {
  id?: number;
  type?: ReportType;
  startPeriod?: Moment;
  endPeriod?: Moment;
  name?: string;
  generatedReportName?: string;
  generatedAIFName?: string;
  generatedReportLocation?: string;
  generatedAIFLocation?: string;
  customer?: ICustomer;
}

export class Report implements IReport {
  constructor(
    public id?: number,
    public type?: ReportType,
    public startPeriod?: Moment,
    public endPeriod?: Moment,
    public name?: string,
    public generatedReportName?: string,
    public generatedAIFName?: string,
    public generatedReportLocation?: string,
    public generatedAIFLocation?: string,
    public customer?: ICustomer
  ) {}
}
