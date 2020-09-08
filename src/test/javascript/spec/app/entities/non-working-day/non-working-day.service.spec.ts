import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { NonWorkingDayService } from 'app/entities/non-working-day/non-working-day.service';
import { INonWorkingDay, NonWorkingDay } from 'app/shared/model/non-working-day.model';

describe('Service Tests', () => {
  describe('NonWorkingDay Service', () => {
    let injector: TestBed;
    let service: NonWorkingDayService;
    let httpMock: HttpTestingController;
    let elemDefault: INonWorkingDay;
    let expectedResult: INonWorkingDay | INonWorkingDay[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(NonWorkingDayService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new NonWorkingDay(0, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            nonWorkingDay: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a NonWorkingDay', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            nonWorkingDay: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            nonWorkingDay: currentDate,
          },
          returnedFromService
        );

        service.create(new NonWorkingDay()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a NonWorkingDay', () => {
        const returnedFromService = Object.assign(
          {
            nonWorkingDay: currentDate.format(DATE_TIME_FORMAT),
            deleted: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            nonWorkingDay: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of NonWorkingDay', () => {
        const returnedFromService = Object.assign(
          {
            nonWorkingDay: currentDate.format(DATE_TIME_FORMAT),
            deleted: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            nonWorkingDay: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a NonWorkingDay', () => {
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
