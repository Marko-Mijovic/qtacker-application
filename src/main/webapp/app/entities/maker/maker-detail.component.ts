import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMaker } from 'app/shared/model/maker.model';

@Component({
  selector: 'jhi-maker-detail',
  templateUrl: './maker-detail.component.html'
})
export class MakerDetailComponent implements OnInit {
  maker: IMaker | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ maker }) => (this.maker = maker));
  }

  previousState(): void {
    window.history.back();
  }
}
