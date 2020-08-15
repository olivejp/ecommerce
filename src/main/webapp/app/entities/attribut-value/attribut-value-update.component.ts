import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAttributValue, AttributValue } from 'app/shared/model/attribut-value.model';
import { AttributValueService } from './attribut-value.service';
import { IAttribut } from 'app/shared/model/attribut.model';
import { AttributService } from 'app/entities/attribut/attribut.service';

@Component({
  selector: 'jhi-attribut-value-update',
  templateUrl: './attribut-value-update.component.html',
})
export class AttributValueUpdateComponent implements OnInit {
  isSaving = false;
  attributs: IAttribut[] = [];

  editForm = this.fb.group({
    id: [],
    value: [],
    attribut: [],
  });

  constructor(
    protected attributValueService: AttributValueService,
    protected attributService: AttributService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attributValue }) => {
      this.updateForm(attributValue);

      this.attributService.query().subscribe((res: HttpResponse<IAttribut[]>) => (this.attributs = res.body || []));
    });
  }

  updateForm(attributValue: IAttributValue): void {
    this.editForm.patchValue({
      id: attributValue.id,
      value: attributValue.value,
      attribut: attributValue.attribut,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attributValue = this.createFromForm();
    if (attributValue.id !== undefined) {
      this.subscribeToSaveResponse(this.attributValueService.update(attributValue));
    } else {
      this.subscribeToSaveResponse(this.attributValueService.create(attributValue));
    }
  }

  private createFromForm(): IAttributValue {
    return {
      ...new AttributValue(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      attribut: this.editForm.get(['attribut'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttributValue>>): void {
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

  trackById(index: number, item: IAttribut): any {
    return item.id;
  }
}
