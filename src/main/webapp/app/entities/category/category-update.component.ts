import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICategory, Category } from 'app/shared/model/category.model';
import { CategoryService } from './category.service';

@Component({
  selector: 'jhi-category-update',
  templateUrl: './category-update.component.html',
})
export class CategoryUpdateComponent implements OnInit {
  isSaving = false;
  categoryparents: ICategory[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    transiant: [],
    categoryParentId: [],
  });

  constructor(protected categoryService: CategoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ category }) => {
      this.updateForm(category);

      this.categoryService
        .query({ filter: 'category-is-null' })
        .pipe(
          map((res: HttpResponse<ICategory[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICategory[]) => {
          if (!category.categoryParentId) {
            this.categoryparents = resBody;
          } else {
            this.categoryService
              .find(category.categoryParentId)
              .pipe(
                map((subRes: HttpResponse<ICategory>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICategory[]) => (this.categoryparents = concatRes));
          }
        });
    });
  }

  updateForm(category: ICategory): void {
    this.editForm.patchValue({
      id: category.id,
      name: category.name,
      transiant: category.transiant,
      categoryParentId: category.categoryParentId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const category = this.createFromForm();
    if (category.id !== undefined) {
      this.subscribeToSaveResponse(this.categoryService.update(category));
    } else {
      this.subscribeToSaveResponse(this.categoryService.create(category));
    }
  }

  private createFromForm(): ICategory {
    return {
      ...new Category(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      transiant: this.editForm.get(['transiant'])!.value,
      categoryParentId: this.editForm.get(['categoryParentId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategory>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICategory): any {
    return item.id;
  }
}
