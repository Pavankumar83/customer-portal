import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICustomerPhone, CustomerPhone } from 'app/shared/model/customer-phone.model';
import { CustomerPhoneService } from './customer-phone.service';
import { CustomerPhoneComponent } from './customer-phone.component';
import { CustomerPhoneDetailComponent } from './customer-phone-detail.component';
import { CustomerPhoneUpdateComponent } from './customer-phone-update.component';

@Injectable({ providedIn: 'root' })
export class CustomerPhoneResolve implements Resolve<ICustomerPhone> {
  constructor(private service: CustomerPhoneService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomerPhone> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((customerPhone: HttpResponse<CustomerPhone>) => {
          if (customerPhone.body) {
            return of(customerPhone.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CustomerPhone());
  }
}

export const customerPhoneRoute: Routes = [
  {
    path: '',
    component: CustomerPhoneComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.customerPhone.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomerPhoneDetailComponent,
    resolve: {
      customerPhone: CustomerPhoneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.customerPhone.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomerPhoneUpdateComponent,
    resolve: {
      customerPhone: CustomerPhoneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.customerPhone.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomerPhoneUpdateComponent,
    resolve: {
      customerPhone: CustomerPhoneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.customerPhone.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
