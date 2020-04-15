import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILogUser } from 'app/shared/model/log-user.model';
import { LogUserService } from './log-user.service';

@Component({
  templateUrl: './log-user-delete-dialog.component.html'
})
export class LogUserDeleteDialogComponent {
  logUser?: ILogUser;

  constructor(protected logUserService: LogUserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.logUserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('logUserListModification');
      this.activeModal.close();
    });
  }
}
