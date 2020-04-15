import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILogUser, LogUser } from 'app/shared/model/log-user.model';
import { LogUserService } from './log-user.service';

@Component({
  selector: 'jhi-log-user-update',
  templateUrl: './log-user-update.component.html'
})
export class LogUserUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    dateTimeLog: []
  });

  constructor(protected logUserService: LogUserService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ logUser }) => {
      if (!logUser.id) {
        const today = moment().startOf('day');
        logUser.dateTimeLog = today;
      }

      this.updateForm(logUser);
    });
  }

  updateForm(logUser: ILogUser): void {
    this.editForm.patchValue({
      id: logUser.id,
      dateTimeLog: logUser.dateTimeLog ? logUser.dateTimeLog.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const logUser = this.createFromForm();
    if (logUser.id !== undefined) {
      this.subscribeToSaveResponse(this.logUserService.update(logUser));
    } else {
      this.subscribeToSaveResponse(this.logUserService.create(logUser));
    }
  }

  private createFromForm(): ILogUser {
    return {
      ...new LogUser(),
      id: this.editForm.get(['id'])!.value,
      dateTimeLog: this.editForm.get(['dateTimeLog'])!.value
        ? moment(this.editForm.get(['dateTimeLog'])!.value, DATE_TIME_FORMAT)
        : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILogUser>>): void {
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
