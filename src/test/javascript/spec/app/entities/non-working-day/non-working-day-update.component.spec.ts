import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CustomerPortalTestModule } from '../../../test.module';
import { NonWorkingDayUpdateComponent } from 'app/entities/non-working-day/non-working-day-update.component';
import { NonWorkingDayService } from 'app/entities/non-working-day/non-working-day.service';
import { NonWorkingDay } from 'app/shared/model/non-working-day.model';

describe('Component Tests', () => {
  describe('NonWorkingDay Management Update Component', () => {
    let comp: NonWorkingDayUpdateComponent;
    let fixture: ComponentFixture<NonWorkingDayUpdateComponent>;
    let service: NonWorkingDayService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [NonWorkingDayUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(NonWorkingDayUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NonWorkingDayUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NonWorkingDayService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NonWorkingDay(123);
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
        const entity = new NonWorkingDay();
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
