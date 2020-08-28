import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CustomerPortalTestModule } from '../../../test.module';
import { CustomerPhoneComponent } from 'app/entities/customer-phone/customer-phone.component';
import { CustomerPhoneService } from 'app/entities/customer-phone/customer-phone.service';
import { CustomerPhone } from 'app/shared/model/customer-phone.model';

describe('Component Tests', () => {
  describe('CustomerPhone Management Component', () => {
    let comp: CustomerPhoneComponent;
    let fixture: ComponentFixture<CustomerPhoneComponent>;
    let service: CustomerPhoneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [CustomerPhoneComponent],
      })
        .overrideTemplate(CustomerPhoneComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerPhoneComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerPhoneService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CustomerPhone(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.customerPhones && comp.customerPhones[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
