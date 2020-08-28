import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CustomerPortalTestModule } from '../../../test.module';
import { BankInfoDetailComponent } from 'app/entities/bank-info/bank-info-detail.component';
import { BankInfo } from 'app/shared/model/bank-info.model';

describe('Component Tests', () => {
  describe('BankInfo Management Detail Component', () => {
    let comp: BankInfoDetailComponent;
    let fixture: ComponentFixture<BankInfoDetailComponent>;
    const route = ({ data: of({ bankInfo: new BankInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [BankInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BankInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BankInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bankInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bankInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
