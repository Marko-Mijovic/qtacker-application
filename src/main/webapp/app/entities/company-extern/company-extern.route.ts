import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICompanyExtern, CompanyExtern } from 'app/shared/model/company-extern.model';
import { CompanyExternService } from './company-extern.service';
import { CompanyExternComponent } from './company-extern.component';
import { CompanyExternDetailComponent } from './company-extern-detail.component';
import { CompanyExternUpdateComponent } from './company-extern-update.component';

@Injectable({ providedIn: 'root' })
export class CompanyExternResolve implements Resolve<ICompanyExtern> {
  constructor(private service: CompanyExternService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompanyExtern> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((companyExtern: HttpResponse<CompanyExtern>) => {
          if (companyExtern.body) {
            return of(companyExtern.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CompanyExtern());
  }
}

export const companyExternRoute: Routes = [
  {
    path: '',
    component: CompanyExternComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'qtackerApplicationApp.companyExtern.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CompanyExternDetailComponent,
    resolve: {
      companyExtern: CompanyExternResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'qtackerApplicationApp.companyExtern.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CompanyExternUpdateComponent,
    resolve: {
      companyExtern: CompanyExternResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'qtackerApplicationApp.companyExtern.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CompanyExternUpdateComponent,
    resolve: {
      companyExtern: CompanyExternResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'qtackerApplicationApp.companyExtern.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
