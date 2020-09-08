import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CustomerPortalTestModule } from '../../../test.module';
import { AvailableTransactionDetailComponent } from 'app/entities/available-transaction/available-transaction-detail.component';
import { AvailableTransaction } from 'app/shared/model/available-transaction.model';

describe('Component Tests', () => {
  describe('AvailableTransaction Management Detail Component', () => {
    let comp: AvailableTransactionDetailComponent;
    let fixture: ComponentFixture<AvailableTransactionDetailComponent>;
    const route = ({ data: of({ availableTransaction: new AvailableTransaction(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [AvailableTransactionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AvailableTransactionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvailableTransactionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load availableTransaction on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.availableTransaction).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
