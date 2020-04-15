import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMaker, Maker } from 'app/shared/model/maker.model';
import { MakerService } from './maker.service';

@Component({
  selector: 'jhi-maker-update',
  templateUrl: './maker-update.component.html'
})
export class MakerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    makerName: []
  });

  constructor(protected makerService: MakerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ maker }) => {
      this.updateForm(maker);
    });
  }

  updateForm(maker: IMaker): void {
    this.editForm.patchValue({
      id: maker.id,
      makerName: maker.makerName
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const maker = this.createFromForm();
    if (maker.id !== undefined) {
      this.subscribeToSaveResponse(this.makerService.update(maker));
    } else {
      this.subscribeToSaveResponse(this.makerService.create(maker));
    }
  }

  private createFromForm(): IMaker {
    return {
      ...new Maker(),
      id: this.editForm.get(['id'])!.value,
      makerName: this.editForm.get(['makerName'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMaker>>): void {
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
}
