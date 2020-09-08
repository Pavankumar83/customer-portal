import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CustomerPortalTestModule } from '../../../test.module';
import { OrderProductComponent } from 'app/entities/order-product/order-product.component';
import { OrderProductService } from 'app/entities/order-product/order-product.service';
import { OrderProduct } from 'app/shared/model/order-product.model';

describe('Component Tests', () => {
  describe('OrderProduct Management Component', () => {
    let comp: OrderProductComponent;
    let fixture: ComponentFixture<OrderProductComponent>;
    let service: OrderProductService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [OrderProductComponent],
      })
        .overrideTemplate(OrderProductComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderProductComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderProductService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OrderProduct(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.orderProducts && comp.orderProducts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
