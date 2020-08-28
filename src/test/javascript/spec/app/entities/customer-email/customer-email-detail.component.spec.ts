import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CustomerPortalTestModule } from '../../../test.module';
import { CustomerEmailDetailComponent } from 'app/entities/customer-email/customer-email-detail.component';
import { CustomerEmail } from 'app/shared/model/customer-email.model';

describe('Component Tests', () => {
  describe('CustomerEmail Management Detail Component', () => {
    let comp: CustomerEmailDetailComponent;
    let fixture: ComponentFixture<CustomerEmailDetailComponent>;
    const route = ({ data: of({ customerEmail: new CustomerEmail(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [CustomerEmailDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CustomerEmailDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerEmailDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load customerEmail on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customerEmail).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
