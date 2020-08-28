import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CustomerPortalTestModule } from '../../../test.module';
import { BankInfoUpdateComponent } from 'app/entities/bank-info/bank-info-update.component';
import { BankInfoService } from 'app/entities/bank-info/bank-info.service';
import { BankInfo } from 'app/shared/model/bank-info.model';

describe('Component Tests', () => {
  describe('BankInfo Management Update Component', () => {
    let comp: BankInfoUpdateComponent;
    let fixture: ComponentFixture<BankInfoUpdateComponent>;
    let service: BankInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [BankInfoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BankInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BankInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BankInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BankInfo(123);
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
        const entity = new BankInfo();
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
