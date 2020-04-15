import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QtackerApplicationSharedModule } from 'app/shared/shared.module';
import { ModelComponent } from './model.component';
import { ModelDetailComponent } from './model-detail.component';
import { ModelUpdateComponent } from './model-update.component';
import { ModelDeleteDialogComponent } from './model-delete-dialog.component';
import { modelRoute } from './model.route';

@NgModule({
  imports: [QtackerApplicationSharedModule, RouterModule.forChild(modelRoute)],
  declarations: [ModelComponent, ModelDetailComponent, ModelUpdateComponent, ModelDeleteDialogComponent],
  entryComponents: [ModelDeleteDialogComponent]
})
export class QtackerApplicationModelModule {}
