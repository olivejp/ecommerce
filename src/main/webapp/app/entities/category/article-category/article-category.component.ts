import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs';
import {JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

import {IArticle} from 'app/shared/model/article.model';

import {ITEMS_PER_PAGE} from 'app/shared/constants/pagination.constants';
import {ICategory} from "app/shared/model/category.model";
import {ArticleService} from "app/entities/article/article.service";
import {ArticleDeleteDialogComponent} from "app/entities/article/article-delete-dialog.component";

@Component({
  selector: 'jhi-article-category',
  templateUrl: './article-category.component.html',
})
export class ArticleCategoryComponent implements OnInit, OnDestroy {
  articles: IArticle[];
  loadAllSubscription?: Subscription;
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  currentSearch: string;

  private category_: ICategory | undefined;

  @Input()
  set category(cat: ICategory | undefined) {
    this.category_ = cat;
    this.reset();
  }

  constructor(
    protected articleService: ArticleService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute
  ) {
    this.articles = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(idCategory: number | undefined): void {
    const searhOption = {
      query: this.currentSearch,
      page: this.page,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    if (idCategory) {
      Object.assign(searhOption, {idCategory});
    }

    if (this.loadAllSubscription) {
      this.loadAllSubscription.unsubscribe();
    }

    this.loadAllSubscription = this.articleService
      .search(searhOption)
      .subscribe((res: HttpResponse<IArticle[]>) => this.paginateArticles(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.articles = [];
    this.loadAll(this.category_?.id);
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll(this.category_?.id);
  }

  search(query: string): void {
    this.articles = [];
    this.links = {
      last: 0,
    };
    this.page = 0;
    if (query) {
      this.predicate = '_score';
      this.ascending = false;
    } else {
      this.predicate = 'id';
      this.ascending = true;
    }
    this.currentSearch = query;
    this.loadAll(this.category_?.id);
  }

  ngOnInit(): void {
    this.loadAll(this.category_?.id);
    this.registerChangeInArticles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
    if (this.loadAllSubscription) {
      this.loadAllSubscription.unsubscribe();
    }
  }

  trackId(index: number, item: IArticle): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInArticles(): void {
    this.eventSubscriber = this.eventManager.subscribe('articleListModification', () => this.reset());
  }

  delete(article: IArticle): void {
    const modalRef = this.modalService.open(ArticleDeleteDialogComponent, {size: 'lg', backdrop: 'static'});
    modalRef.componentInstance.article = article;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateArticles(data: IArticle[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.articles.push(data[i]);
      }
    }
  }

  reindex(): void {
    this.articleService.reindex()
      .subscribe(() => console.log('Reindexed'));
  }
}
