import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { BankInfoService } from 'app/entities/bank-info/bank-info.service';
import { IBankInfo, BankInfo } from 'app/shared/model/bank-info.model';

describe('Service Tests', () => {
  describe('BankInfo Service', () => {
    let injector: TestBed;
    let service: BankInfoService;
    let httpMock: HttpTestingController;
    let elemDefault: IBankInfo;
    let expectedResult: IBankInfo | IBankInfo[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BankInfoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new BankInfo(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a BankInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new BankInfo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BankInfo', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            accountHolder: 'BBBBBB',
            accountNumber: 'BBBBBB',
            branchCode: 'BBBBBB',
            branchAddress: 'BBBBBB',
            ifscCode: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BankInfo', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            accountHolder: 'BBBBBB',
            accountNumber: 'BBBBBB',
            branchCode: 'BBBBBB',
            branchAddress: 'BBBBBB',
            ifscCode: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a BankInfo', () => {
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
