import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICustomerAddress, CustomerAddress } from 'app/shared/model/customer-address.model';
import { CustomerAddressService } from './customer-address.service';
import { CustomerAddressComponent } from './customer-address.component';
import { CustomerAddressDetailComponent } from './customer-address-detail.component';
import { CustomerAddressUpdateComponent } from './customer-address-update.component';

@Injectable({ providedIn: 'root' })
export class CustomerAddressResolve implements Resolve<ICustomerAddress> {
  constructor(private service: CustomerAddressService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomerAddress> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((customerAddress: HttpResponse<CustomerAddress>) => {
          if (customerAddress.body) {
            return of(customerAddress.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CustomerAddress());
  }
}

export const customerAddressRoute: Routes = [
  {
    path: '',
    component: CustomerAddressComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.customerAddress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomerAddressDetailComponent,
    resolve: {
      customerAddress: CustomerAddressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.customerAddress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomerAddressUpdateComponent,
    resolve: {
      customerAddress: CustomerAddressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.customerAddress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomerAddressUpdateComponent,
    resolve: {
      customerAddress: CustomerAddressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.customerAddress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
