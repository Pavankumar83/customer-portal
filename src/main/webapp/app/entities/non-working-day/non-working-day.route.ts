import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INonWorkingDay, NonWorkingDay } from 'app/shared/model/non-working-day.model';
import { NonWorkingDayService } from './non-working-day.service';
import { NonWorkingDayComponent } from './non-working-day.component';
import { NonWorkingDayDetailComponent } from './non-working-day-detail.component';
import { NonWorkingDayUpdateComponent } from './non-working-day-update.component';

@Injectable({ providedIn: 'root' })
export class NonWorkingDayResolve implements Resolve<INonWorkingDay> {
  constructor(private service: NonWorkingDayService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INonWorkingDay> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((nonWorkingDay: HttpResponse<NonWorkingDay>) => {
          if (nonWorkingDay.body) {
            return of(nonWorkingDay.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NonWorkingDay());
  }
}

export const nonWorkingDayRoute: Routes = [
  {
    path: '',
    component: NonWorkingDayComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.nonWorkingDay.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NonWorkingDayDetailComponent,
    resolve: {
      nonWorkingDay: NonWorkingDayResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.nonWorkingDay.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NonWorkingDayUpdateComponent,
    resolve: {
      nonWorkingDay: NonWorkingDayResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.nonWorkingDay.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NonWorkingDayUpdateComponent,
    resolve: {
      nonWorkingDay: NonWorkingDayResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.nonWorkingDay.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
