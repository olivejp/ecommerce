import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IModel } from 'app/shared/model/model.model';
import { ModelService } from './model.service';
import { ModelDeleteDialogComponent } from './model-delete-dialog.component';

@Component({
  selector: 'jhi-model',
  templateUrl: './model.component.html',
})
export class ModelComponent implements OnInit, OnDestroy {
  models?: IModel[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected modelService: ModelService,
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
      this.modelService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IModel[]>) => (this.models = res.body || []));
      return;
    }

    this.modelService.query().subscribe((res: HttpResponse<IModel[]>) => (this.models = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInModels();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IModel): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInModels(): void {
    this.eventSubscriber = this.eventManager.subscribe('modelListModification', () => this.loadAll());
  }

  delete(model: IModel): void {
    const modalRef = this.modalService.open(ModelDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.model = model;
  }
}
