import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAttribut } from 'app/shared/model/attribut.model';
import { AttributService } from './attribut.service';

@Component({
  templateUrl: './attribut-delete-dialog.component.html',
})
export class AttributDeleteDialogComponent {
  attribut?: IAttribut;

  constructor(protected attributService: AttributService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.attributService.delete(id).subscribe(() => {
      this.eventManager.broadcast('attributListModification');
      this.activeModal.close();
    });
  }
}
