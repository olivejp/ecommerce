import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {EcommerceSharedModule} from 'app/shared/shared.module';
import {CategoryComponent} from './category.component';
import {CategoryDetailComponent} from './category-detail.component';
import {CategoryUpdateComponent} from './category-update.component';
import {CategoryDeleteDialogComponent} from './category-delete-dialog.component';
import {categoryRoute} from './category.route';
import {CategoryDropdownComponent} from "app/entities/category/category-dropdown.component";
import {MatInputModule} from "@angular/material/input";
import {MatTreeModule} from '@angular/material/tree';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from "@angular/material/form-field";
import {DragDropModule} from "@angular/cdk/drag-drop";
import {MatIconModule} from '@angular/material/icon';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatOptionModule } from '@angular/material/core';

@NgModule({
  imports: [EcommerceSharedModule, RouterModule.forChild(categoryRoute), MatOptionModule,MatTreeModule, MatButtonModule, MatIconModule, MatFormFieldModule, DragDropModule, MatInputModule, MatAutocompleteModule],
  declarations: [CategoryComponent, CategoryDetailComponent, CategoryUpdateComponent, CategoryDeleteDialogComponent, CategoryDropdownComponent],
  entryComponents: [CategoryDeleteDialogComponent],
})
export class EcommerceCategoryModule {
}
