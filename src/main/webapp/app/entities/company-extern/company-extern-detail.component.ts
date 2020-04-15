import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompanyExtern } from 'app/shared/model/company-extern.model';

@Component({
  selector: 'jhi-company-extern-detail',
  templateUrl: './company-extern-detail.component.html'
})
export class CompanyExternDetailComponent implements OnInit {
  companyExtern: ICompanyExtern | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyExtern }) => (this.companyExtern = companyExtern));
  }

  previousState(): void {
    window.history.back();
  }
}
