import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILogUser } from 'app/shared/model/log-user.model';

@Component({
  selector: 'jhi-log-user-detail',
  templateUrl: './log-user-detail.component.html'
})
export class LogUserDetailComponent implements OnInit {
  logUser: ILogUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ logUser }) => (this.logUser = logUser));
  }

  previousState(): void {
    window.history.back();
  }
}
