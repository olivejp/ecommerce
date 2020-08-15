import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAttributArticle, AttributArticle } from 'app/shared/model/attribut-article.model';
import { AttributArticleService } from './attribut-article.service';
import { AttributArticleComponent } from './attribut-article.component';
import { AttributArticleDetailComponent } from './attribut-article-detail.component';
import { AttributArticleUpdateComponent } from './attribut-article-update.component';

@Injectable({ providedIn: 'root' })
export class AttributArticleResolve implements Resolve<IAttributArticle> {
  constructor(private service: AttributArticleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAttributArticle> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((attributArticle: HttpResponse<AttributArticle>) => {
          if (attributArticle.body) {
            return of(attributArticle.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AttributArticle());
  }
}

export const attributArticleRoute: Routes = [
  {
    path: '',
    component: AttributArticleComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecommerceApp.attributArticle.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AttributArticleDetailComponent,
    resolve: {
      attributArticle: AttributArticleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecommerceApp.attributArticle.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AttributArticleUpdateComponent,
    resolve: {
      attributArticle: AttributArticleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecommerceApp.attributArticle.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AttributArticleUpdateComponent,
    resolve: {
      attributArticle: AttributArticleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecommerceApp.attributArticle.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
