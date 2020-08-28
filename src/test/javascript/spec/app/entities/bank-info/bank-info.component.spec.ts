import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CustomerPortalTestModule } from '../../../test.module';
import { BankInfoComponent } from 'app/entities/bank-info/bank-info.component';
import { BankInfoService } from 'app/entities/bank-info/bank-info.service';
import { BankInfo } from 'app/shared/model/bank-info.model';

describe('Component Tests', () => {
  describe('BankInfo Management Component', () => {
    let comp: BankInfoComponent;
    let fixture: ComponentFixture<BankInfoComponent>;
    let service: BankInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [BankInfoComponent],
      })
        .overrideTemplate(BankInfoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BankInfoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BankInfoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BankInfo(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bankInfos && comp.bankInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
