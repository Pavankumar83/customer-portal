import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICustomerEmail, CustomerEmail } from 'app/shared/model/customer-email.model';
import { CustomerEmailService } from './customer-email.service';
import { CustomerEmailComponent } from './customer-email.component';
import { CustomerEmailDetailComponent } from './customer-email-detail.component';
import { CustomerEmailUpdateComponent } from './customer-email-update.component';

@Injectable({ providedIn: 'root' })
export class CustomerEmailResolve implements Resolve<ICustomerEmail> {
  constructor(private service: CustomerEmailService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomerEmail> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((customerEmail: HttpResponse<CustomerEmail>) => {
          if (customerEmail.body) {
            return of(customerEmail.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CustomerEmail());
  }
}

export const customerEmailRoute: Routes = [
  {
    path: '',
    component: CustomerEmailComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.customerEmail.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomerEmailDetailComponent,
    resolve: {
      customerEmail: CustomerEmailResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.customerEmail.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomerEmailUpdateComponent,
    resolve: {
      customerEmail: CustomerEmailResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.customerEmail.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomerEmailUpdateComponent,
    resolve: {
      customerEmail: CustomerEmailResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.customerEmail.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
