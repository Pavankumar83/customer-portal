import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CustomerPortalTestModule } from '../../../test.module';
import { CustomerPhoneUpdateComponent } from 'app/entities/customer-phone/customer-phone-update.component';
import { CustomerPhoneService } from 'app/entities/customer-phone/customer-phone.service';
import { CustomerPhone } from 'app/shared/model/customer-phone.model';

describe('Component Tests', () => {
  describe('CustomerPhone Management Update Component', () => {
    let comp: CustomerPhoneUpdateComponent;
    let fixture: ComponentFixture<CustomerPhoneUpdateComponent>;
    let service: CustomerPhoneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [CustomerPhoneUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CustomerPhoneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerPhoneUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerPhoneService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustomerPhone(123);
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
        const entity = new CustomerPhone();
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
