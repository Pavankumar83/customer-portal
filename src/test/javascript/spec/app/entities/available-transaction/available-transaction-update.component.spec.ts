import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CustomerPortalTestModule } from '../../../test.module';
import { AvailableTransactionUpdateComponent } from 'app/entities/available-transaction/available-transaction-update.component';
import { AvailableTransactionService } from 'app/entities/available-transaction/available-transaction.service';
import { AvailableTransaction } from 'app/shared/model/available-transaction.model';

describe('Component Tests', () => {
  describe('AvailableTransaction Management Update Component', () => {
    let comp: AvailableTransactionUpdateComponent;
    let fixture: ComponentFixture<AvailableTransactionUpdateComponent>;
    let service: AvailableTransactionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [AvailableTransactionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AvailableTransactionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvailableTransactionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvailableTransactionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AvailableTransaction(123);
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
        const entity = new AvailableTransaction();
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
