import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ReportService } from 'app/entities/report/report.service';
import { IReport, Report } from 'app/shared/model/report.model';
import { ReportType } from 'app/shared/model/enumerations/report-type.model';

describe('Service Tests', () => {
  describe('Report Service', () => {
    let injector: TestBed;
    let service: ReportService;
    let httpMock: HttpTestingController;
    let elemDefault: IReport;
    let expectedResult: IReport | IReport[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ReportService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Report(0, ReportType.IT3B, currentDate, currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            startPeriod: currentDate.format(DATE_TIME_FORMAT),
            endPeriod: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Report', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startPeriod: currentDate.format(DATE_TIME_FORMAT),
            endPeriod: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startPeriod: currentDate,
            endPeriod: currentDate,
          },
          returnedFromService
        );

        service.create(new Report()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Report', () => {
        const returnedFromService = Object.assign(
          {
            type: 'BBBBBB',
            startPeriod: currentDate.format(DATE_TIME_FORMAT),
            endPeriod: currentDate.format(DATE_TIME_FORMAT),
            name: 'BBBBBB',
            generatedReportName: 'BBBBBB',
            generatedAIFName: 'BBBBBB',
            generatedReportLocation: 'BBBBBB',
            generatedAIFLocation: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startPeriod: currentDate,
            endPeriod: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Report', () => {
        const returnedFromService = Object.assign(
          {
            type: 'BBBBBB',
            startPeriod: currentDate.format(DATE_TIME_FORMAT),
            endPeriod: currentDate.format(DATE_TIME_FORMAT),
            name: 'BBBBBB',
            generatedReportName: 'BBBBBB',
            generatedAIFName: 'BBBBBB',
            generatedReportLocation: 'BBBBBB',
            generatedAIFLocation: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startPeriod: currentDate,
            endPeriod: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Report', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
