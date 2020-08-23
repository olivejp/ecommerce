import {Injectable} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {ActivatedRouteSnapshot, Resolve, Router, Routes} from '@angular/router';
import {EMPTY, Observable, of} from 'rxjs';
import {flatMap} from 'rxjs/operators';

import {Authority} from 'app/shared/constants/authority.constants';
import {UserRouteAccessService} from 'app/core/auth/user-route-access-service';
import {Category, ICategory} from 'app/shared/model/category.model';
import {CategoryService} from './category.service';
import {CategoryComponent} from './category.component';
import {CategoryDetailComponent} from './category-detail.component';
import {CategoryUpdateComponent} from "app/entities/category/category-update.component";

@Injectable({providedIn: 'root'})
export class CategoryResolve implements Resolve<ICategory> {
  constructor(private service: CategoryService, private router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<ICategory> | Observable<never> {
    const id = route.params['categoryId'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((category: HttpResponse<Category>) => {
          if (category.body) {
            return of(category.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Category());
  }
}

export const categoryRoute: Routes = [
  {
    path: '',
    component: CategoryComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecommerceApp.category.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':categoryId/edit',
    component: CategoryUpdateComponent,
    resolve: {
      category: CategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecommerceApp.category.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':categoryId/view',
    component: CategoryDetailComponent,
    resolve: {
      category: CategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ecommerceApp.category.home.title',
    },
    canActivate: [UserRouteAccessService],
    outlet: 'categoryOutlet'
  }
];
