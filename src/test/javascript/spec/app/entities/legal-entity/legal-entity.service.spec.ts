import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { LegalEntityService } from 'app/entities/legal-entity/legal-entity.service';
import { ILegalEntity, LegalEntity } from 'app/shared/model/legal-entity.model';

describe('Service Tests', () => {
  describe('LegalEntity Service', () => {
    let injector: TestBed;
    let service: LegalEntityService;
    let httpMock: HttpTestingController;
    let elemDefault: ILegalEntity;
    let expectedResult: ILegalEntity | ILegalEntity[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LegalEntityService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new LegalEntity(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, false, 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateOfStart: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a LegalEntity', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateOfStart: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfStart: currentDate,
          },
          returnedFromService
        );

        service.create(new LegalEntity()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a LegalEntity', () => {
        const returnedFromService = Object.assign(
          {
            commercialName: 'BBBBBB',
            taxNumber: 'BBBBBB',
            title: 'BBBBBB',
            dateOfStart: currentDate.format(DATE_TIME_FORMAT),
            businessClosed: true,
            businessArea: 'BBBBBB',
            deleted: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfStart: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of LegalEntity', () => {
        const returnedFromService = Object.assign(
          {
            commercialName: 'BBBBBB',
            taxNumber: 'BBBBBB',
            title: 'BBBBBB',
            dateOfStart: currentDate.format(DATE_TIME_FORMAT),
            businessClosed: true,
            businessArea: 'BBBBBB',
            deleted: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfStart: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a LegalEntity', () => {
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
