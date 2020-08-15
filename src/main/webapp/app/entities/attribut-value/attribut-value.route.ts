import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAttributValue, AttributValue } from 'app/shared/model/attribut-value.model';
import { AttributValueService } from './attribut-value.service';
import { AttributValueComponent } from './attribut-value.component';
import { AttributValueDetailComponent } from './attribut-value-detail.component';
import { AttributValueUpdateComponent } from './attribut-value-update.component';

@Injectable({ providedIn: 'root' })
export class AttributValueResolve implements Resolve<IAttributValue> {
  constructor(private service: AttributValueService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAttributValue> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((attributValue: HttpResponse<AttributValue>) => {
          if (attributValue.body) {
            return of(attributValue.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AttributValue());
  }
}

export const attributValueRoute: Routes = [
  {
    path: '',
    component: AttributValueComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecommerceApp.attributValue.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AttributValueDetailComponent,
    resolve: {
      attributValue: AttributValueResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecommerceApp.attributValue.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AttributValueUpdateComponent,
    resolve: {
      attributValue: AttributValueResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecommerceApp.attributValue.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AttributValueUpdateComponent,
    resolve: {
      attributValue: AttributValueResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecommerceApp.attributValue.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
