import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAttributArticle } from 'app/shared/model/attribut-article.model';
import { AttributArticleService } from './attribut-article.service';
import { AttributArticleDeleteDialogComponent } from './attribut-article-delete-dialog.component';

@Component({
  selector: 'jhi-attribut-article',
  templateUrl: './attribut-article.component.html',
})
export class AttributArticleComponent implements OnInit, OnDestroy {
  attributArticles?: IAttributArticle[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected attributArticleService: AttributArticleService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.attributArticleService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IAttributArticle[]>) => (this.attributArticles = res.body || []));
      return;
    }

    this.attributArticleService.query().subscribe((res: HttpResponse<IAttributArticle[]>) => (this.attributArticles = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAttributArticles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAttributArticle): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAttributArticles(): void {
    this.eventSubscriber = this.eventManager.subscribe('attributArticleListModification', () => this.loadAll());
  }

  delete(attributArticle: IAttributArticle): void {
    const modalRef = this.modalService.open(AttributArticleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.attributArticle = attributArticle;
  }
}
