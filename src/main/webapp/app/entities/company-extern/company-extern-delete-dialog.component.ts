import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompanyExtern } from 'app/shared/model/company-extern.model';
import { CompanyExternService } from './company-extern.service';

@Component({
  templateUrl: './company-extern-delete-dialog.component.html'
})
export class CompanyExternDeleteDialogComponent {
  companyExtern?: ICompanyExtern;

  constructor(
    protected companyExternService: CompanyExternService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.companyExternService.delete(id).subscribe(() => {
      this.eventManager.broadcast('companyExternListModification');
      this.activeModal.close();
    });
  }
}
