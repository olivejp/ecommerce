import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IAttributArticle, AttributArticle } from 'app/shared/model/attribut-article.model';
import { AttributArticleService } from './attribut-article.service';
import { IAttributValue } from 'app/shared/model/attribut-value.model';
import { AttributValueService } from 'app/entities/attribut-value/attribut-value.service';
import { IArticle } from 'app/shared/model/article.model';
import { ArticleService } from 'app/entities/article/article.service';

type SelectableEntity = IAttributValue | IArticle;

@Component({
  selector: 'jhi-attribut-article-update',
  templateUrl: './attribut-article-update.component.html',
})
export class AttributArticleUpdateComponent implements OnInit {
  isSaving = false;
  attributs: IAttributValue[] = [];
  articles: IArticle[] = [];

  editForm = this.fb.group({
    id: [],
    value: [],
    attributId: [],
    articleId: [],
  });

  constructor(
    protected attributArticleService: AttributArticleService,
    protected attributValueService: AttributValueService,
    protected articleService: ArticleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attributArticle }) => {
      this.updateForm(attributArticle);

      this.attributValueService
        .query({ filter: 'attributarticle-is-null' })
        .pipe(
          map((res: HttpResponse<IAttributValue[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAttributValue[]) => {
          if (!attributArticle.attributId) {
            this.attributs = resBody;
          } else {
            this.attributValueService
              .find(attributArticle.attributId)
              .pipe(
                map((subRes: HttpResponse<IAttributValue>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAttributValue[]) => (this.attributs = concatRes));
          }
        });

      this.articleService.query().subscribe((res: HttpResponse<IArticle[]>) => (this.articles = res.body || []));
    });
  }

  updateForm(attributArticle: IAttributArticle): void {
    this.editForm.patchValue({
      id: attributArticle.id,
      value: attributArticle.value,
      attributId: attributArticle.attributId,
      articleId: attributArticle.articleId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attributArticle = this.createFromForm();
    if (attributArticle.id !== undefined) {
      this.subscribeToSaveResponse(this.attributArticleService.update(attributArticle));
    } else {
      this.subscribeToSaveResponse(this.attributArticleService.create(attributArticle));
    }
  }

  private createFromForm(): IAttributArticle {
    return {
      ...new AttributArticle(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      attributId: this.editForm.get(['attributId'])!.value,
      articleId: this.editForm.get(['articleId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttributArticle>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
