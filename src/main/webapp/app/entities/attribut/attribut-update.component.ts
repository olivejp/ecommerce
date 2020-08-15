import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAttribut, Attribut } from 'app/shared/model/attribut.model';
import { AttributService } from './attribut.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';

@Component({
  selector: 'jhi-attribut-update',
  templateUrl: './attribut-update.component.html',
})
export class AttributUpdateComponent implements OnInit {
  isSaving = false;
  categories: ICategory[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    category: [],
  });

  constructor(
    protected attributService: AttributService,
    protected categoryService: CategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attribut }) => {
      this.updateForm(attribut);

      this.categoryService.query().subscribe((res: HttpResponse<ICategory[]>) => (this.categories = res.body || []));
    });
  }

  updateForm(attribut: IAttribut): void {
    this.editForm.patchValue({
      id: attribut.id,
      name: attribut.name,
      category: attribut.category,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attribut = this.createFromForm();
    if (attribut.id !== undefined) {
      this.subscribeToSaveResponse(this.attributService.update(attribut));
    } else {
      this.subscribeToSaveResponse(this.attributService.create(attribut));
    }
  }

  private createFromForm(): IAttribut {
    return {
      ...new Attribut(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      category: this.editForm.get(['category'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttribut>>): void {
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
