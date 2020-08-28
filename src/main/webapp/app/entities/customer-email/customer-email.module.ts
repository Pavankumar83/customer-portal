import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CustomerPortalSharedModule } from 'app/shared/shared.module';
import { CustomerEmailComponent } from './customer-email.component';
import { CustomerEmailDetailComponent } from './customer-email-detail.component';
import { CustomerEmailUpdateComponent } from './customer-email-update.component';
import { CustomerEmailDeleteDialogComponent } from './customer-email-delete-dialog.component';
import { customerEmailRoute } from './customer-email.route';

@NgModule({
  imports: [CustomerPortalSharedModule, RouterModule.forChild(customerEmailRoute)],
  declarations: [CustomerEmailComponent, CustomerEmailDetailComponent, CustomerEmailUpdateComponent, CustomerEmailDeleteDialogComponent],
  entryComponents: [CustomerEmailDeleteDialogComponent],
})
export class CustomerPortalCustomerEmailModule {}
