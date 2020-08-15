import {NgModule} from '@angular/core';
import {CategoryDropdownComponent} from './category-dropdown.component';
import {MatTreeModule} from '@angular/material/tree';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatFormFieldModule} from "@angular/material/form-field";
import {DragDropModule} from "@angular/cdk/drag-drop";
import {MatInputModule} from "@angular/material/input";
import {EcommerceCategoryModule} from "app/entities/category/category.module";
import {EcommerceSharedModule} from "app/shared/shared.module";
import {EcommerceEntityModule} from "app/entities/entity.module";

@NgModule({
  imports: [EcommerceSharedModule, EcommerceEntityModule, MatTreeModule, MatButtonModule, MatIconModule, MatFormFieldModule, DragDropModule, MatInputModule, EcommerceCategoryModule],
  declarations: [CategoryDropdownComponent],
  exports: [CategoryDropdownComponent]
})
export class CategoryDropdownModule {
}
