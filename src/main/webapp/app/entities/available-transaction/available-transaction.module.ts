import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CustomerPortalSharedModule } from 'app/shared/shared.module';
import { AvailableTransactionComponent } from './available-transaction.component';
import { AvailableTransactionDetailComponent } from './available-transaction-detail.component';
import { AvailableTransactionUpdateComponent } from './available-transaction-update.component';
import { AvailableTransactionDeleteDialogComponent } from './available-transaction-delete-dialog.component';
import { availableTransactionRoute } from './available-transaction.route';

@NgModule({
  imports: [CustomerPortalSharedModule, RouterModule.forChild(availableTransactionRoute)],
  declarations: [
    AvailableTransactionComponent,
    AvailableTransactionDetailComponent,
    AvailableTransactionUpdateComponent,
    AvailableTransactionDeleteDialogComponent,
  ],
  entryComponents: [AvailableTransactionDeleteDialogComponent],
})
export class CustomerPortalAvailableTransactionModule {}
