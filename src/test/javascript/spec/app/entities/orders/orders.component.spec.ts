import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CustomerPortalTestModule } from '../../../test.module';
import { OrdersComponent } from 'app/entities/orders/orders.component';
import { OrdersService } from 'app/entities/orders/orders.service';
import { Orders } from 'app/shared/model/orders.model';

describe('Component Tests', () => {
  describe('Orders Management Component', () => {
    let comp: OrdersComponent;
    let fixture: ComponentFixture<OrdersComponent>;
    let service: OrdersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CustomerPortalTestModule],
        declarations: [OrdersComponent],
      })
        .overrideTemplate(OrdersComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrdersComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrdersService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Orders(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.orders && comp.orders[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
