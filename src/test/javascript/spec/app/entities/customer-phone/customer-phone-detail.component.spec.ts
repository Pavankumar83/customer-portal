import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CustomerPortalTestModule } from '../../../test.module';
import { CustomerPhoneDetailComponent } from 'app/entities/customer-phone/customer-phone-detail.component';
import { CustomerPhone } from 'app/shared/model/customer-phone.model';

describe('Component Tests', () => {
  describe('CustomerPhone Management Detail Component', () => {
    let comp: CustomerPhoneDetailComponent;
    let fixture: ComponentFixture<CustomerPhoneDetailComponent>;
    const route = ({ data: of({ customerPhone: new CustomerPhone(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [CustomerPhoneDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CustomerPhoneDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerPhoneDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load customerPhone on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customerPhone).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
