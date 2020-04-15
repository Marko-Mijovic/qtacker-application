import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICompanyExtern, CompanyExtern } from 'app/shared/model/company-extern.model';
import { CompanyExternService } from './company-extern.service';

@Component({
  selector: 'jhi-company-extern-update',
  templateUrl: './company-extern-update.component.html'
})
export class CompanyExternUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: []
  });

  constructor(protected companyExternService: CompanyExternService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyExtern }) => {
      this.updateForm(companyExtern);
    });
  }

  updateForm(companyExtern: ICompanyExtern): void {
    this.editForm.patchValue({
      id: companyExtern.id
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const companyExtern = this.createFromForm();
    if (companyExtern.id !== undefined) {
      this.subscribeToSaveResponse(this.companyExternService.update(companyExtern));
    } else {
      this.subscribeToSaveResponse(this.companyExternService.create(companyExtern));
    }
  }

  private createFromForm(): ICompanyExtern {
    return {
      ...new CompanyExtern(),
      id: this.editForm.get(['id'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanyExtern>>): void {
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
