import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceIntervention } from 'app/shared/model/service-intervention.model';

@Component({
  selector: 'jhi-service-intervention-detail',
  templateUrl: './service-intervention-detail.component.html'
})
export class ServiceInterventionDetailComponent implements OnInit {
  serviceIntervention: IServiceIntervention | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceIntervention }) => (this.serviceIntervention = serviceIntervention));
  }

  previousState(): void {
    window.history.back();
  }
}
