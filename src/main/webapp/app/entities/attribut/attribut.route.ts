import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAttribut, Attribut } from 'app/shared/model/attribut.model';
import { AttributService } from './attribut.service';
import { AttributComponent } from './attribut.component';
import { AttributDetailComponent } from './attribut-detail.component';
import { AttributUpdateComponent } from './attribut-update.component';

@Injectable({ providedIn: 'root' })
export class AttributResolve implements Resolve<IAttribut> {
  constructor(private service: AttributService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAttribut> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((attribut: HttpResponse<Attribut>) => {
          if (attribut.body) {
            return of(attribut.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Attribut());
  }
}

export const attributRoute: Routes = [
  {
    path: '',
    component: AttributComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecommerceApp.attribut.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AttributDetailComponent,
    resolve: {
      attribut: AttributResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecommerceApp.attribut.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AttributUpdateComponent,
    resolve: {
      attribut: AttributResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecommerceApp.attribut.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AttributUpdateComponent,
    resolve: {
      attribut: AttributResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecommerceApp.attribut.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
