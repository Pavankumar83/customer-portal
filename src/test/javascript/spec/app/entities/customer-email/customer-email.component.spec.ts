import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CustomerPortalTestModule } from '../../../test.module';
import { CustomerEmailComponent } from 'app/entities/customer-email/customer-email.component';
import { CustomerEmailService } from 'app/entities/customer-email/customer-email.service';
import { CustomerEmail } from 'app/shared/model/customer-email.model';

describe('Component Tests', () => {
  describe('CustomerEmail Management Component', () => {
    let comp: CustomerEmailComponent;
    let fixture: ComponentFixture<CustomerEmailComponent>;
    let service: CustomerEmailService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [CustomerEmailComponent],
      })
        .overrideTemplate(CustomerEmailComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerEmailComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerEmailService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CustomerEmail(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.customerEmails && comp.customerEmails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
