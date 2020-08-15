import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAttributArticle } from 'app/shared/model/attribut-article.model';
import { AttributArticleService } from './attribut-article.service';

@Component({
  templateUrl: './attribut-article-delete-dialog.component.html',
})
export class AttributArticleDeleteDialogComponent {
  attributArticle?: IAttributArticle;

  constructor(
    protected attributArticleService: AttributArticleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.attributArticleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('attributArticleListModification');
      this.activeModal.close();
    });
  }
}
