import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILegalEntity, LegalEntity } from 'app/shared/model/legal-entity.model';
import { LegalEntityService } from './legal-entity.service';
import { LegalEntityComponent } from './legal-entity.component';
import { LegalEntityDetailComponent } from './legal-entity-detail.component';
import { LegalEntityUpdateComponent } from './legal-entity-update.component';

@Injectable({ providedIn: 'root' })
export class LegalEntityResolve implements Resolve<ILegalEntity> {
  constructor(private service: LegalEntityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILegalEntity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((legalEntity: HttpResponse<LegalEntity>) => {
          if (legalEntity.body) {
            return of(legalEntity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LegalEntity());
  }
}

export const legalEntityRoute: Routes = [
  {
    path: '',
    component: LegalEntityComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.legalEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LegalEntityDetailComponent,
    resolve: {
      legalEntity: LegalEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.legalEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LegalEntityUpdateComponent,
    resolve: {
      legalEntity: LegalEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.legalEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LegalEntityUpdateComponent,
    resolve: {
      legalEntity: LegalEntityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'customerPortalApp.legalEntity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
