import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QtackerApplicationSharedModule } from 'app/shared/shared.module';
import { ServiceInterventionComponent } from './service-intervention.component';
import { ServiceInterventionDetailComponent } from './service-intervention-detail.component';
import { ServiceInterventionUpdateComponent } from './service-intervention-update.component';
import { ServiceInterventionDeleteDialogComponent } from './service-intervention-delete-dialog.component';
import { serviceInterventionRoute } from './service-intervention.route';

@NgModule({
  imports: [QtackerApplicationSharedModule, RouterModule.forChild(serviceInterventionRoute)],
  declarations: [
    ServiceInterventionComponent,
    ServiceInterventionDetailComponent,
    ServiceInterventionUpdateComponent,
    ServiceInterventionDeleteDialogComponent
  ],
  entryComponents: [ServiceInterventionDeleteDialogComponent]
})
export class QtackerApplicationServiceInterventionModule {}
