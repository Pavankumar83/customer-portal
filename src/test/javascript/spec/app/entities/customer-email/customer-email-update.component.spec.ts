import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CustomerPortalTestModule } from '../../../test.module';
import { CustomerEmailUpdateComponent } from 'app/entities/customer-email/customer-email-update.component';
import { CustomerEmailService } from 'app/entities/customer-email/customer-email.service';
import { CustomerEmail } from 'app/shared/model/customer-email.model';

describe('Component Tests', () => {
  describe('CustomerEmail Management Update Component', () => {
    let comp: CustomerEmailUpdateComponent;
    let fixture: ComponentFixture<CustomerEmailUpdateComponent>;
    let service: CustomerEmailService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [CustomerEmailUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CustomerEmailUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerEmailUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerEmailService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustomerEmail(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustomerEmail();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
