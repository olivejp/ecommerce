import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICommande, Commande } from 'app/shared/model/commande.model';
import { CommandeService } from './commande.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';

@Component({
  selector: 'jhi-commande-update',
  templateUrl: './commande-update.component.html',
})
export class CommandeUpdateComponent implements OnInit {
  isSaving = false;
  clients: IClient[] = [];

  editForm = this.fb.group({
    id: [],
    date: [],
    datePayment: [],
    clientId: [],
  });

  constructor(
    protected commandeService: CommandeService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commande }) => {
      if (!commande.id) {
        const today = moment().startOf('day');
        commande.date = today;
        commande.datePayment = today;
      }

      this.updateForm(commande);

      this.clientService.query().subscribe((res: HttpResponse<IClient[]>) => (this.clients = res.body || []));
    });
  }

  updateForm(commande: ICommande): void {
    this.editForm.patchValue({
      id: commande.id,
      date: commande.date ? commande.date.format(DATE_TIME_FORMAT) : null,
      datePayment: commande.datePayment ? commande.datePayment.format(DATE_TIME_FORMAT) : null,
      clientId: commande.clientId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commande = this.createFromForm();
    if (commande.id !== undefined) {
      this.subscribeToSaveResponse(this.commandeService.update(commande));
    } else {
      this.subscribeToSaveResponse(this.commandeService.create(commande));
    }
  }

  private createFromForm(): ICommande {
    return {
      ...new Commande(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      datePayment: this.editForm.get(['datePayment'])!.value
        ? moment(this.editForm.get(['datePayment'])!.value, DATE_TIME_FORMAT)
        : undefined,
      clientId: this.editForm.get(['clientId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommande>>): void {
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

  trackById(index: number, item: IClient): any {
    return item.id;
  }
}
