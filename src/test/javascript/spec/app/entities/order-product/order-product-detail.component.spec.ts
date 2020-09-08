import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CustomerPortalTestModule } from '../../../test.module';
import { OrderProductDetailComponent } from 'app/entities/order-product/order-product-detail.component';
import { OrderProduct } from 'app/shared/model/order-product.model';

describe('Component Tests', () => {
  describe('OrderProduct Management Detail Component', () => {
    let comp: OrderProductDetailComponent;
    let fixture: ComponentFixture<OrderProductDetailComponent>;
    const route = ({ data: of({ orderProduct: new OrderProduct(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [OrderProductDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OrderProductDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderProductDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load orderProduct on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orderProduct).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
