import {NgModule} from '@angular/core';

import {EcommerceSharedModule} from 'app/shared/shared.module';
import {ArticleCategoryComponent} from "app/entities/category/article-category/article-category.component";
import { RouterModule } from '@angular/router';


@NgModule({
  imports: [RouterModule,EcommerceSharedModule],
  declarations: [ArticleCategoryComponent],
  exports: [ArticleCategoryComponent]
})
export class EcommerceArticleCategoryModule {
}
