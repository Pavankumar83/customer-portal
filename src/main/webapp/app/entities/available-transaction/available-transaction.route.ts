import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAvailableTransaction, AvailableTransaction } from 'app/shared/model/available-transaction.model';
import { AvailableTransactionService } from './available-transaction.service';
import { AvailableTransactionComponent } from './available-transaction.component';
import { AvailableTransactionDetailComponent } from './available-transaction-detail.component';
import { AvailableTransactionUpdateComponent } from './available-transaction-update.component';

@Injectable({ providedIn: 'root' })
export class AvailableTransactionResolve implements Resolve<IAvailableTransaction> {
  constructor(private service: AvailableTransactionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAvailableTransaction> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((availableTransaction: HttpResponse<AvailableTransaction>) => {
          if (availableTransaction.body) {
            return of(availableTransaction.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AvailableTransaction());
  }
}

export const availableTransactionRoute: Routes = [
  {
    path: '',
    component: AvailableTransactionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.availableTransaction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AvailableTransactionDetailComponent,
    resolve: {
      availableTransaction: AvailableTransactionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.availableTransaction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AvailableTransactionUpdateComponent,
    resolve: {
      availableTransaction: AvailableTransactionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.availableTransaction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AvailableTransactionUpdateComponent,
    resolve: {
      availableTransaction: AvailableTransactionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.availableTransaction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
