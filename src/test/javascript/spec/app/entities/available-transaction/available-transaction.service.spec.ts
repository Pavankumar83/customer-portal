import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AvailableTransactionService } from 'app/entities/available-transaction/available-transaction.service';
import { IAvailableTransaction, AvailableTransaction } from 'app/shared/model/available-transaction.model';
import { TransactionType } from 'app/shared/model/enumerations/transaction-type.model';
import { TransactionMode } from 'app/shared/model/enumerations/transaction-mode.model';

describe('Service Tests', () => {
  describe('AvailableTransaction Service', () => {
    let injector: TestBed;
    let service: AvailableTransactionService;
    let httpMock: HttpTestingController;
    let elemDefault: IAvailableTransaction;
    let expectedResult: IAvailableTransaction | IAvailableTransaction[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AvailableTransactionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AvailableTransaction(0, 'AAAAAAA', TransactionType.INTEREST, TransactionMode.DEBIT, 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateOfTransaction: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AvailableTransaction', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateOfTransaction: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfTransaction: currentDate,
          },
          returnedFromService
        );

        service.create(new AvailableTransaction()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AvailableTransaction', () => {
        const returnedFromService = Object.assign(
          {
            transactionId: 'BBBBBB',
            transactionType: 'BBBBBB',
            transactionMode: 'BBBBBB',
            transAmount: 1,
            dateOfTransaction: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfTransaction: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AvailableTransaction', () => {
        const returnedFromService = Object.assign(
          {
            transactionId: 'BBBBBB',
            transactionType: 'BBBBBB',
            transactionMode: 'BBBBBB',
            transAmount: 1,
            dateOfTransaction: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfTransaction: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AvailableTransaction', () => {
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
