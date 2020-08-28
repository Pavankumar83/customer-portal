import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBankInfo, BankInfo } from 'app/shared/model/bank-info.model';
import { BankInfoService } from './bank-info.service';
import { BankInfoComponent } from './bank-info.component';
import { BankInfoDetailComponent } from './bank-info-detail.component';
import { BankInfoUpdateComponent } from './bank-info-update.component';

@Injectable({ providedIn: 'root' })
export class BankInfoResolve implements Resolve<IBankInfo> {
  constructor(private service: BankInfoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBankInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bankInfo: HttpResponse<BankInfo>) => {
          if (bankInfo.body) {
            return of(bankInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BankInfo());
  }
}

export const bankInfoRoute: Routes = [
  {
    path: '',
    component: BankInfoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.bankInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BankInfoDetailComponent,
    resolve: {
      bankInfo: BankInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.bankInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BankInfoUpdateComponent,
    resolve: {
      bankInfo: BankInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.bankInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BankInfoUpdateComponent,
    resolve: {
      bankInfo: BankInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.bankInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
