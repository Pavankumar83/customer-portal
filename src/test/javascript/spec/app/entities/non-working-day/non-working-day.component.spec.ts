import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CustomerPortalTestModule } from '../../../test.module';
import { NonWorkingDayComponent } from 'app/entities/non-working-day/non-working-day.component';
import { NonWorkingDayService } from 'app/entities/non-working-day/non-working-day.service';
import { NonWorkingDay } from 'app/shared/model/non-working-day.model';

describe('Component Tests', () => {
  describe('NonWorkingDay Management Component', () => {
    let comp: NonWorkingDayComponent;
    let fixture: ComponentFixture<NonWorkingDayComponent>;
    let service: NonWorkingDayService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [NonWorkingDayComponent],
      })
        .overrideTemplate(NonWorkingDayComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NonWorkingDayComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NonWorkingDayService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new NonWorkingDay(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.nonWorkingDays && comp.nonWorkingDays[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
