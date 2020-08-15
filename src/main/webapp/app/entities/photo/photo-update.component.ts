import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPhoto, Photo } from 'app/shared/model/photo.model';
import { PhotoService } from './photo.service';
import { IModel } from 'app/shared/model/model.model';
import { ModelService } from 'app/entities/model/model.service';

@Component({
  selector: 'jhi-photo-update',
  templateUrl: './photo-update.component.html',
})
export class PhotoUpdateComponent implements OnInit {
  isSaving = false;
  models: IModel[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    url: [],
    model: [],
  });

  constructor(
    protected photoService: PhotoService,
    protected modelService: ModelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ photo }) => {
      this.updateForm(photo);

      this.modelService.query().subscribe((res: HttpResponse<IModel[]>) => (this.models = res.body || []));
    });
  }

  updateForm(photo: IPhoto): void {
    this.editForm.patchValue({
      id: photo.id,
      name: photo.name,
      url: photo.url,
      model: photo.model,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const photo = this.createFromForm();
    if (photo.id !== undefined) {
      this.subscribeToSaveResponse(this.photoService.update(photo));
    } else {
      this.subscribeToSaveResponse(this.photoService.create(photo));
    }
  }

  private createFromForm(): IPhoto {
    return {
      ...new Photo(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      url: this.editForm.get(['url'])!.value,
      model: this.editForm.get(['model'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhoto>>): void {
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

  trackById(index: number, item: IModel): any {
    return item.id;
  }
}
