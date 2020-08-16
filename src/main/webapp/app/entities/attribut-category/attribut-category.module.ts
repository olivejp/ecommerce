import {NgModule} from '@angular/core';

import {EcommerceSharedModule} from 'app/shared/shared.module';
import {AttributCategoryComponent} from "app/entities/attribut-category/attribut-category.component";

@NgModule({
  imports: [EcommerceSharedModule],
  declarations: [AttributCategoryComponent],
  exports: [
    AttributCategoryComponent
  ]
})
export class EcommerceAttributCategoryModule {
}
