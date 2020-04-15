import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.QtackerApplicationAddressModule)
      },
      {
        path: 'company',
        loadChildren: () => import('./company/company.module').then(m => m.QtackerApplicationCompanyModule)
      },
      {
        path: 'company-extern',
        loadChildren: () => import('./company-extern/company-extern.module').then(m => m.QtackerApplicationCompanyExternModule)
      },
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.QtackerApplicationDepartmentModule)
      },
      {
        path: 'device',
        loadChildren: () => import('./device/device.module').then(m => m.QtackerApplicationDeviceModule)
      },
      {
        path: 'log-user',
        loadChildren: () => import('./log-user/log-user.module').then(m => m.QtackerApplicationLogUserModule)
      },
      {
        path: 'maker',
        loadChildren: () => import('./maker/maker.module').then(m => m.QtackerApplicationMakerModule)
      },
      {
        path: 'model',
        loadChildren: () => import('./model/model.module').then(m => m.QtackerApplicationModelModule)
      },
      {
        path: 'service-intervention',
        loadChildren: () =>
          import('./service-intervention/service-intervention.module').then(m => m.QtackerApplicationServiceInterventionModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class QtackerApplicationEntityModule {}
