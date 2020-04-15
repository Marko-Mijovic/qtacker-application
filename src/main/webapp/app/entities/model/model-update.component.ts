import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IModel, Model } from 'app/shared/model/model.model';
import { ModelService } from './model.service';
import { IMaker } from 'app/shared/model/maker.model';
import { MakerService } from 'app/entities/maker/maker.service';

@Component({
  selector: 'jhi-model-update',
  templateUrl: './model-update.component.html'
})
export class ModelUpdateComponent implements OnInit {
  isSaving = false;
  makers: IMaker[] = [];

  editForm = this.fb.group({
    id: [],
    modelName: [],
    makerId: []
  });

  constructor(
    protected modelService: ModelService,
    protected makerService: MakerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ model }) => {
      this.updateForm(model);

      this.makerService.query().subscribe((res: HttpResponse<IMaker[]>) => (this.makers = res.body || []));
    });
  }

  updateForm(model: IModel): void {
    this.editForm.patchValue({
      id: model.id,
      modelName: model.modelName,
      makerId: model.makerId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const model = this.createFromForm();
    if (model.id !== undefined) {
      this.subscribeToSaveResponse(this.modelService.update(model));
    } else {
      this.subscribeToSaveResponse(this.modelService.create(model));
    }
  }

  private createFromForm(): IModel {
    return {
      ...new Model(),
      id: this.editForm.get(['id'])!.value,
      modelName: this.editForm.get(['modelName'])!.value,
      makerId: this.editForm.get(['makerId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModel>>): void {
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

  trackById(index: number, item: IMaker): any {
    return item.id;
  }
}
