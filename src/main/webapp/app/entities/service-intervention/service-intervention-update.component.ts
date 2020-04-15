import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IServiceIntervention, ServiceIntervention } from 'app/shared/model/service-intervention.model';
import { ServiceInterventionService } from './service-intervention.service';
import { IDevice } from 'app/shared/model/device.model';
import { DeviceService } from 'app/entities/device/device.service';
import { ICompanyExtern } from 'app/shared/model/company-extern.model';
import { CompanyExternService } from 'app/entities/company-extern/company-extern.service';

type SelectableEntity = IDevice | ICompanyExtern;

@Component({
  selector: 'jhi-service-intervention-update',
  templateUrl: './service-intervention-update.component.html'
})
export class ServiceInterventionUpdateComponent implements OnInit {
  isSaving = false;
  devices: IDevice[] = [];
  companyexterns: ICompanyExtern[] = [];

  editForm = this.fb.group({
    id: [],
    dateTime: [],
    price: [],
    description: [],
    deviceId: [],
    companyExternId: []
  });

  constructor(
    protected serviceInterventionService: ServiceInterventionService,
    protected deviceService: DeviceService,
    protected companyExternService: CompanyExternService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceIntervention }) => {
      if (!serviceIntervention.id) {
        const today = moment().startOf('day');
        serviceIntervention.dateTime = today;
      }

      this.updateForm(serviceIntervention);

      this.deviceService.query().subscribe((res: HttpResponse<IDevice[]>) => (this.devices = res.body || []));

      this.companyExternService.query().subscribe((res: HttpResponse<ICompanyExtern[]>) => (this.companyexterns = res.body || []));
    });
  }

  updateForm(serviceIntervention: IServiceIntervention): void {
    this.editForm.patchValue({
      id: serviceIntervention.id,
      dateTime: serviceIntervention.dateTime ? serviceIntervention.dateTime.format(DATE_TIME_FORMAT) : null,
      price: serviceIntervention.price,
      description: serviceIntervention.description,
      deviceId: serviceIntervention.deviceId,
      companyExternId: serviceIntervention.companyExternId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const serviceIntervention = this.createFromForm();
    if (serviceIntervention.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceInterventionService.update(serviceIntervention));
    } else {
      this.subscribeToSaveResponse(this.serviceInterventionService.create(serviceIntervention));
    }
  }

  private createFromForm(): IServiceIntervention {
    return {
      ...new ServiceIntervention(),
      id: this.editForm.get(['id'])!.value,
      dateTime: this.editForm.get(['dateTime'])!.value ? moment(this.editForm.get(['dateTime'])!.value, DATE_TIME_FORMAT) : undefined,
      price: this.editForm.get(['price'])!.value,
      description: this.editForm.get(['description'])!.value,
      deviceId: this.editForm.get(['deviceId'])!.value,
      companyExternId: this.editForm.get(['companyExternId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceIntervention>>): void {
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
