import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ILigneCommande, LigneCommande } from 'app/shared/model/ligne-commande.model';
import { LigneCommandeService } from './ligne-commande.service';
import { IModel } from 'app/shared/model/model.model';
import { ModelService } from 'app/entities/model/model.service';
import { ICommande } from 'app/shared/model/commande.model';
import { CommandeService } from 'app/entities/commande/commande.service';

type SelectableEntity = IModel | ICommande;

@Component({
  selector: 'jhi-ligne-commande-update',
  templateUrl: './ligne-commande-update.component.html',
})
export class LigneCommandeUpdateComponent implements OnInit {
  isSaving = false;
  models: IModel[] = [];
  commandes: ICommande[] = [];

  editForm = this.fb.group({
    id: [],
    total: [],
    number: [],
    modelId: [],
    commandeId: [],
  });

  constructor(
    protected ligneCommandeService: LigneCommandeService,
    protected modelService: ModelService,
    protected commandeService: CommandeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ligneCommande }) => {
      this.updateForm(ligneCommande);

      this.modelService
        .query({ filter: 'lignecommande-is-null' })
        .pipe(
          map((res: HttpResponse<IModel[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IModel[]) => {
          if (!ligneCommande.modelId) {
            this.models = resBody;
          } else {
            this.modelService
              .find(ligneCommande.modelId)
              .pipe(
                map((subRes: HttpResponse<IModel>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IModel[]) => (this.models = concatRes));
          }
        });

      this.commandeService.query().subscribe((res: HttpResponse<ICommande[]>) => (this.commandes = res.body || []));
    });
  }

  updateForm(ligneCommande: ILigneCommande): void {
    this.editForm.patchValue({
      id: ligneCommande.id,
      total: ligneCommande.total,
      number: ligneCommande.number,
      modelId: ligneCommande.modelId,
      commandeId: ligneCommande.commandeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ligneCommande = this.createFromForm();
    if (ligneCommande.id !== undefined) {
      this.subscribeToSaveResponse(this.ligneCommandeService.update(ligneCommande));
    } else {
      this.subscribeToSaveResponse(this.ligneCommandeService.create(ligneCommande));
    }
  }

  private createFromForm(): ILigneCommande {
    return {
      ...new LigneCommande(),
      id: this.editForm.get(['id'])!.value,
      total: this.editForm.get(['total'])!.value,
      number: this.editForm.get(['number'])!.value,
      modelId: this.editForm.get(['modelId'])!.value,
      commandeId: this.editForm.get(['commandeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILigneCommande>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
