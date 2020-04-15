import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMaker } from 'app/shared/model/maker.model';
import { MakerService } from './maker.service';

@Component({
  templateUrl: './maker-delete-dialog.component.html'
})
export class MakerDeleteDialogComponent {
  maker?: IMaker;

  constructor(protected makerService: MakerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.makerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('makerListModification');
      this.activeModal.close();
    });
  }
}
