import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CustomerPortalSharedModule } from 'app/shared/shared.module';
import { LegalEntityComponent } from './legal-entity.component';
import { LegalEntityDetailComponent } from './legal-entity-detail.component';
import { LegalEntityUpdateComponent } from './legal-entity-update.component';
import { LegalEntityDeleteDialogComponent } from './legal-entity-delete-dialog.component';
import { legalEntityRoute } from './legal-entity.route';

@NgModule({
  imports: [CustomerPortalSharedModule, RouterModule.forChild(legalEntityRoute)],
  declarations: [LegalEntityComponent, LegalEntityDetailComponent, LegalEntityUpdateComponent, LegalEntityDeleteDialogComponent],
  entryComponents: [LegalEntityDeleteDialogComponent],
})
export class CustomerPortalLegalEntityModule {}
