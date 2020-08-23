import {NgModule} from '@angular/core';

import {EcommerceSharedModule} from 'app/shared/shared.module';
import {AttributCategoryComponent} from "app/entities/category/attribut-category/attribut-category.component";
import {RouterModule} from '@angular/router';

@NgModule({
  imports: [RouterModule, EcommerceSharedModule],
  declarations: [AttributCategoryComponent],
  exports: [AttributCategoryComponent]
})
export class EcommerceAttributCategoryModule {
}
