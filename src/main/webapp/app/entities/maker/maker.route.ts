import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMaker, Maker } from 'app/shared/model/maker.model';
import { MakerService } from './maker.service';
import { MakerComponent } from './maker.component';
import { MakerDetailComponent } from './maker-detail.component';
import { MakerUpdateComponent } from './maker-update.component';

@Injectable({ providedIn: 'root' })
export class MakerResolve implements Resolve<IMaker> {
  constructor(private service: MakerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMaker> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((maker: HttpResponse<Maker>) => {
          if (maker.body) {
            return of(maker.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Maker());
  }
}

export const makerRoute: Routes = [
  {
    path: '',
    component: MakerComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'qtackerApplicationApp.maker.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MakerDetailComponent,
    resolve: {
      maker: MakerResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'qtackerApplicationApp.maker.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MakerUpdateComponent,
    resolve: {
      maker: MakerResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'qtackerApplicationApp.maker.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MakerUpdateComponent,
    resolve: {
      maker: MakerResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'qtackerApplicationApp.maker.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
