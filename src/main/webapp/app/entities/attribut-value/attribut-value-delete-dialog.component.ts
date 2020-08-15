import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAttributValue } from 'app/shared/model/attribut-value.model';
import { AttributValueService } from './attribut-value.service';

@Component({
  templateUrl: './attribut-value-delete-dialog.component.html',
})
export class AttributValueDeleteDialogComponent {
  attributValue?: IAttributValue;

  constructor(
    protected attributValueService: AttributValueService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.attributValueService.delete(id).subscribe(() => {
      this.eventManager.broadcast('attributValueListModification');
      this.activeModal.close();
    });
  }
}
