import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QtackerApplicationSharedModule } from 'app/shared/shared.module';
import { MakerComponent } from './maker.component';
import { MakerDetailComponent } from './maker-detail.component';
import { MakerUpdateComponent } from './maker-update.component';
import { MakerDeleteDialogComponent } from './maker-delete-dialog.component';
import { makerRoute } from './maker.route';

@NgModule({
  imports: [QtackerApplicationSharedModule, RouterModule.forChild(makerRoute)],
  declarations: [MakerComponent, MakerDetailComponent, MakerUpdateComponent, MakerDeleteDialogComponent],
  entryComponents: [MakerDeleteDialogComponent]
})
export class QtackerApplicationMakerModule {}
