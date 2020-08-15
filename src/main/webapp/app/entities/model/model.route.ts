import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IModel, Model } from 'app/shared/model/model.model';
import { ModelService } from './model.service';
import { ModelComponent } from './model.component';
import { ModelDetailComponent } from './model-detail.component';
import { ModelUpdateComponent } from './model-update.component';

@Injectable({ providedIn: 'root' })
export class ModelResolve implements Resolve<IModel> {
  constructor(private service: ModelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IModel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((model: HttpResponse<Model>) => {
          if (model.body) {
            return of(model.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Model());
  }
}

export const modelRoute: Routes = [
  {
    path: '',
    component: ModelComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecommerceApp.model.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ModelDetailComponent,
    resolve: {
      model: ModelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecommerceApp.model.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ModelUpdateComponent,
    resolve: {
      model: ModelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecommerceApp.model.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ModelUpdateComponent,
    resolve: {
      model: ModelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecommerceApp.model.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
