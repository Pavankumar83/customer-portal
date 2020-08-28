import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CustomerPortalTestModule } from '../../../test.module';
import { InstitutionComponent } from 'app/entities/institution/institution.component';
import { InstitutionService } from 'app/entities/institution/institution.service';
import { Institution } from 'app/shared/model/institution.model';

describe('Component Tests', () => {
  describe('Institution Management Component', () => {
    let comp: InstitutionComponent;
    let fixture: ComponentFixture<InstitutionComponent>;
    let service: InstitutionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [InstitutionComponent],
      })
        .overrideTemplate(InstitutionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InstitutionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InstitutionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Institution(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.institutions && comp.institutions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
