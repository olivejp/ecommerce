import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {JhiEventManager} from 'ng-jhipster';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

import {IAttribut} from 'app/shared/model/attribut.model';
import {AttributService} from '../attribut/attribut.service';
import {AttributDeleteDialogComponent} from '../attribut/attribut-delete-dialog.component';
import {ICategory} from "app/shared/model/category.model";
import {map} from 'rxjs/operators';

@Component({
  selector: 'jhi-attribut-category',
  templateUrl: './attribut-category.component.html',
})
export class AttributCategoryComponent implements OnInit, OnDestroy {
  category_: ICategory | null | undefined;
  attributs?: IAttribut[];
  eventSubscriber?: Subscription;

  @Input()
  set category(category: ICategory | undefined) {
    this.category_ = category;
    if (this.category_) {
      this.loadAll();
    }
  }

  constructor(
    protected attributService: AttributService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
  }

  loadAll(): void {
    if (this.category_ && this.category_.id) {
      this.attributService.findByCategoryId(this.category_.id)
        .pipe(map(a => a.body))
        .subscribe(attributs => this.attributs = attributs || []);
    }
  }

  ngOnInit(): void {
    this.loadAll();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAttribut): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  delete(attribut: IAttribut): void {
    const modalRef = this.modalService.open(AttributDeleteDialogComponent, {size: 'lg', backdrop: 'static'});
    modalRef.componentInstance.attribut = attribut;
  }
}
