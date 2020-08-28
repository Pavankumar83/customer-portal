import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CustomerPortalTestModule } from '../../../test.module';
import { LegalEntityDetailComponent } from 'app/entities/legal-entity/legal-entity-detail.component';
import { LegalEntity } from 'app/shared/model/legal-entity.model';

describe('Component Tests', () => {
  describe('LegalEntity Management Detail Component', () => {
    let comp: LegalEntityDetailComponent;
    let fixture: ComponentFixture<LegalEntityDetailComponent>;
    const route = ({ data: of({ legalEntity: new LegalEntity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [LegalEntityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LegalEntityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LegalEntityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load legalEntity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.legalEntity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
