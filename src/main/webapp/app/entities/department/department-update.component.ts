import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDepartment, Department } from 'app/shared/model/department.model';
import { DepartmentService } from './department.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';

@Component({
  selector: 'jhi-department-update',
  templateUrl: './department-update.component.html'
})
export class DepartmentUpdateComponent implements OnInit {
  isSaving = false;
  companies: ICompany[] = [];

  editForm = this.fb.group({
    id: [],
    displayName: [],
    location: [],
    headOfDepartment: [],
    description: [],
    companyId: []
  });

  constructor(
    protected departmentService: DepartmentService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ department }) => {
      this.updateForm(department);

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));
    });
  }

  updateForm(department: IDepartment): void {
    this.editForm.patchValue({
      id: department.id,
      displayName: department.displayName,
      location: department.location,
      headOfDepartment: department.headOfDepartment,
      description: department.description,
      companyId: department.companyId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const department = this.createFromForm();
    if (department.id !== undefined) {
      this.subscribeToSaveResponse(this.departmentService.update(department));
    } else {
      this.subscribeToSaveResponse(this.departmentService.create(department));
    }
  }

  private createFromForm(): IDepartment {
    return {
      ...new Department(),
      id: this.editForm.get(['id'])!.value,
      displayName: this.editForm.get(['displayName'])!.value,
      location: this.editForm.get(['location'])!.value,
      headOfDepartment: this.editForm.get(['headOfDepartment'])!.value,
      description: this.editForm.get(['description'])!.value,
      companyId: this.editForm.get(['companyId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartment>>): void {
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

  trackById(index: number, item: ICompany): any {
    return item.id;
  }
}
