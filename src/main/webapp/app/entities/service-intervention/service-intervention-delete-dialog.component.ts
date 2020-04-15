import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceIntervention } from 'app/shared/model/service-intervention.model';
import { ServiceInterventionService } from './service-intervention.service';

@Component({
  templateUrl: './service-intervention-delete-dialog.component.html'
})
export class ServiceInterventionDeleteDialogComponent {
  serviceIntervention?: IServiceIntervention;

  constructor(
    protected serviceInterventionService: ServiceInterventionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.serviceInterventionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('serviceInterventionListModification');
      this.activeModal.close();
    });
  }
}
