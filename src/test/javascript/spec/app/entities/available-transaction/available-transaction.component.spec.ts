import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CustomerPortalTestModule } from '../../../test.module';
import { AvailableTransactionComponent } from 'app/entities/available-transaction/available-transaction.component';
import { AvailableTransactionService } from 'app/entities/available-transaction/available-transaction.service';
import { AvailableTransaction } from 'app/shared/model/available-transaction.model';

describe('Component Tests', () => {
  describe('AvailableTransaction Management Component', () => {
    let comp: AvailableTransactionComponent;
    let fixture: ComponentFixture<AvailableTransactionComponent>;
    let service: AvailableTransactionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [AvailableTransactionComponent],
      })
        .overrideTemplate(AvailableTransactionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvailableTransactionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvailableTransactionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AvailableTransaction(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.availableTransactions && comp.availableTransactions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
