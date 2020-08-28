import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CustomerPortalTestModule } from '../../../test.module';
import { CustomerAddressComponent } from 'app/entities/customer-address/customer-address.component';
import { CustomerAddressService } from 'app/entities/customer-address/customer-address.service';
import { CustomerAddress } from 'app/shared/model/customer-address.model';

describe('Component Tests', () => {
  describe('CustomerAddress Management Component', () => {
    let comp: CustomerAddressComponent;
    let fixture: ComponentFixture<CustomerAddressComponent>;
    let service: CustomerAddressService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [CustomerAddressComponent],
      })
        .overrideTemplate(CustomerAddressComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerAddressComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerAddressService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CustomerAddress(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.customerAddresses && comp.customerAddresses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
