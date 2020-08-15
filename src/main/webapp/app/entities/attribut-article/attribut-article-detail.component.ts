import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAttributArticle } from 'app/shared/model/attribut-article.model';

@Component({
  selector: 'jhi-attribut-article-detail',
  templateUrl: './attribut-article-detail.component.html',
})
export class AttributArticleDetailComponent implements OnInit {
  attributArticle: IAttributArticle | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attributArticle }) => (this.attributArticle = attributArticle));
  }

  previousState(): void {
    window.history.back();
  }
}
