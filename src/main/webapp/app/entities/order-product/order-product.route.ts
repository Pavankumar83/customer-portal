import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrderProduct, OrderProduct } from 'app/shared/model/order-product.model';
import { OrderProductService } from './order-product.service';
import { OrderProductComponent } from './order-product.component';
import { OrderProductDetailComponent } from './order-product-detail.component';
import { OrderProductUpdateComponent } from './order-product-update.component';

@Injectable({ providedIn: 'root' })
export class OrderProductResolve implements Resolve<IOrderProduct> {
  constructor(private service: OrderProductService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrderProduct> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((orderProduct: HttpResponse<OrderProduct>) => {
          if (orderProduct.body) {
            return of(orderProduct.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrderProduct());
  }
}

export const orderProductRoute: Routes = [
  {
    path: '',
    component: OrderProductComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.orderProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrderProductDetailComponent,
    resolve: {
      orderProduct: OrderProductResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.orderProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrderProductUpdateComponent,
    resolve: {
      orderProduct: OrderProductResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.orderProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrderProductUpdateComponent,
    resolve: {
      orderProduct: OrderProductResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.orderProduct.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
