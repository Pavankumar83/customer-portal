import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CustomerPortalTestModule } from '../../../test.module';
import { NonWorkingDayDetailComponent } from 'app/entities/non-working-day/non-working-day-detail.component';
import { NonWorkingDay } from 'app/shared/model/non-working-day.model';

describe('Component Tests', () => {
  describe('NonWorkingDay Management Detail Component', () => {
    let comp: NonWorkingDayDetailComponent;
    let fixture: ComponentFixture<NonWorkingDayDetailComponent>;
    const route = ({ data: of({ nonWorkingDay: new NonWorkingDay(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [NonWorkingDayDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(NonWorkingDayDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NonWorkingDayDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load nonWorkingDay on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nonWorkingDay).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
