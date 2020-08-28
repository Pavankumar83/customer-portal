import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CustomerPortalSharedModule } from 'app/shared/shared.module';
import { InstitutionComponent } from './institution.component';
import { InstitutionDetailComponent } from './institution-detail.component';
import { InstitutionUpdateComponent } from './institution-update.component';
import { InstitutionDeleteDialogComponent } from './institution-delete-dialog.component';
import { institutionRoute } from './institution.route';

@NgModule({
  imports: [CustomerPortalSharedModule, RouterModule.forChild(institutionRoute)],
  declarations: [InstitutionComponent, InstitutionDetailComponent, InstitutionUpdateComponent, InstitutionDeleteDialogComponent],
  entryComponents: [InstitutionDeleteDialogComponent],
})
export class CustomerPortalInstitutionModule {}
