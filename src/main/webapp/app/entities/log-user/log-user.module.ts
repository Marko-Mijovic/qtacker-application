import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QtackerApplicationSharedModule } from 'app/shared/shared.module';
import { LogUserComponent } from './log-user.component';
import { LogUserDetailComponent } from './log-user-detail.component';
import { LogUserUpdateComponent } from './log-user-update.component';
import { LogUserDeleteDialogComponent } from './log-user-delete-dialog.component';
import { logUserRoute } from './log-user.route';

@NgModule({
  imports: [QtackerApplicationSharedModule, RouterModule.forChild(logUserRoute)],
  declarations: [LogUserComponent, LogUserDetailComponent, LogUserUpdateComponent, LogUserDeleteDialogComponent],
  entryComponents: [LogUserDeleteDialogComponent]
})
export class QtackerApplicationLogUserModule {}
