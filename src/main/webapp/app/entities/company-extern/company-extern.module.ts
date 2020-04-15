import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QtackerApplicationSharedModule } from 'app/shared/shared.module';
import { CompanyExternComponent } from './company-extern.component';
import { CompanyExternDetailComponent } from './company-extern-detail.component';
import { CompanyExternUpdateComponent } from './company-extern-update.component';
import { CompanyExternDeleteDialogComponent } from './company-extern-delete-dialog.component';
import { companyExternRoute } from './company-extern.route';

@NgModule({
  imports: [QtackerApplicationSharedModule, RouterModule.forChild(companyExternRoute)],
  declarations: [CompanyExternComponent, CompanyExternDetailComponent, CompanyExternUpdateComponent, CompanyExternDeleteDialogComponent],
  entryComponents: [CompanyExternDeleteDialogComponent]
})
export class QtackerApplicationCompanyExternModule {}
