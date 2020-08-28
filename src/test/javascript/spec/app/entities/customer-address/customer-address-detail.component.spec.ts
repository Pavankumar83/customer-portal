import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CustomerPortalTestModule } from '../../../test.module';
import { CustomerAddressDetailComponent } from 'app/entities/customer-address/customer-address-detail.component';
import { CustomerAddress } from 'app/shared/model/customer-address.model';

describe('Component Tests', () => {
  describe('CustomerAddress Management Detail Component', () => {
    let comp: CustomerAddressDetailComponent;
    let fixture: ComponentFixture<CustomerAddressDetailComponent>;
    const route = ({ data: of({ customerAddress: new CustomerAddress(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [CustomerAddressDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CustomerAddressDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerAddressDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load customerAddress on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customerAddress).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
