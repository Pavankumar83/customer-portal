import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CustomerPortalTestModule } from '../../../test.module';
import { LegalEntityComponent } from 'app/entities/legal-entity/legal-entity.component';
import { LegalEntityService } from 'app/entities/legal-entity/legal-entity.service';
import { LegalEntity } from 'app/shared/model/legal-entity.model';

describe('Component Tests', () => {
  describe('LegalEntity Management Component', () => {
    let comp: LegalEntityComponent;
    let fixture: ComponentFixture<LegalEntityComponent>;
    let service: LegalEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [LegalEntityComponent],
      })
        .overrideTemplate(LegalEntityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LegalEntityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LegalEntityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LegalEntity(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.legalEntities && comp.legalEntities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
