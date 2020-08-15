import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAttributValue } from 'app/shared/model/attribut-value.model';
import { AttributValueService } from './attribut-value.service';
import { AttributValueDeleteDialogComponent } from './attribut-value-delete-dialog.component';

@Component({
  selector: 'jhi-attribut-value',
  templateUrl: './attribut-value.component.html',
})
export class AttributValueComponent implements OnInit, OnDestroy {
  attributValues?: IAttributValue[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected attributValueService: AttributValueService,
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
      this.attributValueService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IAttributValue[]>) => (this.attributValues = res.body || []));
      return;
    }

    this.attributValueService.query().subscribe((res: HttpResponse<IAttributValue[]>) => (this.attributValues = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAttributValues();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAttributValue): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAttributValues(): void {
    this.eventSubscriber = this.eventManager.subscribe('attributValueListModification', () => this.loadAll());
  }

  delete(attributValue: IAttributValue): void {
    const modalRef = this.modalService.open(AttributValueDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.attributValue = attributValue;
  }
}
