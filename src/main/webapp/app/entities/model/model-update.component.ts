import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IModel, Model } from 'app/shared/model/model.model';
import { ModelService } from './model.service';
import { IArticle } from 'app/shared/model/article.model';
import { ArticleService } from 'app/entities/article/article.service';

@Component({
  selector: 'jhi-model-update',
  templateUrl: './model-update.component.html',
})
export class ModelUpdateComponent implements OnInit {
  isSaving = false;
  articles: IArticle[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    count: [],
    article: [],
  });

  constructor(
    protected modelService: ModelService,
    protected articleService: ArticleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ model }) => {
      this.updateForm(model);

      this.articleService.query().subscribe((res: HttpResponse<IArticle[]>) => (this.articles = res.body || []));
    });
  }

  updateForm(model: IModel): void {
    this.editForm.patchValue({
      id: model.id,
      name: model.name,
      count: model.count,
      article: model.article,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const model = this.createFromForm();
    if (model.id !== undefined) {
      this.subscribeToSaveResponse(this.modelService.update(model));
    } else {
      this.subscribeToSaveResponse(this.modelService.create(model));
    }
  }

  private createFromForm(): IModel {
    return {
      ...new Model(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      count: this.editForm.get(['count'])!.value,
      article: this.editForm.get(['article'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModel>>): void {
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

  trackById(index: number, item: IArticle): any {
    return item.id;
  }
}
