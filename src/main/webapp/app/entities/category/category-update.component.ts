import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {FormBuilder, Validators, FormControl} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {Observable, Subscription} from 'rxjs';
import {map, tap} from 'rxjs/operators';

import {Category, ICategory} from 'app/shared/model/category.model';
import {CategoryService, TypeCategory} from './category.service';

@Component({
  selector: 'jhi-category-update',
  templateUrl: './category-update.component.html',
})
export class CategoryUpdateComponent implements OnInit, OnDestroy {
  subscription: Subscription | undefined;
  category_: ICategory | undefined;
  isSaving = false;
  categoryparents: ICategory[] = [];
  myControl = new FormControl();

  @Input()
  set category(category: ICategory | undefined) {
    this.category_ = category;
    if (this.category_) {
      this.updateForm(this.category_);
    }
  }

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    transiant: [],
    categoryParentId: [],
  });

  constructor(protected categoryService: CategoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.findAllParents();

    this.subscription = this.categoryService.listen().subscribe(values => {
      const type: TypeCategory = values[0];

      if (type === TypeCategory.CREATED) {
        this.findAllParents();
      }
    })
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  private findAllParents(): void {
    this.categoryService
      .query({filter: 'category-is-null'})
      .pipe(
        map((res: HttpResponse<ICategory[]>) => {
          return res.body || [];
        })
      )
      .subscribe((resBody: ICategory[]) => this.categoryparents = resBody);
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
      this.subscribeToSaveResponse(this.categoryService.update(category).pipe(tap(a => this.categoryService.emit(TypeCategory.UPDATED, a.body))));
    } else {
      this.subscribeToSaveResponse(this.categoryService.create(category).pipe(tap(a => this.categoryService.emit(TypeCategory.CREATED, a.body))));
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
    result
      .subscribe(
        () => this.onSaveSuccess(),
        () => this.onSaveError()
      );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICategory): any {
    return item.id;
  }
}
