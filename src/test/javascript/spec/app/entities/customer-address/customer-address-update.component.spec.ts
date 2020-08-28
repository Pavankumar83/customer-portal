import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CustomerPortalTestModule } from '../../../test.module';
import { CustomerAddressUpdateComponent } from 'app/entities/customer-address/customer-address-update.component';
import { CustomerAddressService } from 'app/entities/customer-address/customer-address.service';
import { CustomerAddress } from 'app/shared/model/customer-address.model';

describe('Component Tests', () => {
  describe('CustomerAddress Management Update Component', () => {
    let comp: CustomerAddressUpdateComponent;
    let fixture: ComponentFixture<CustomerAddressUpdateComponent>;
    let service: CustomerAddressService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [CustomerAddressUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CustomerAddressUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerAddressUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerAddressService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustomerAddress(123);
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
        const entity = new CustomerAddress();
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
