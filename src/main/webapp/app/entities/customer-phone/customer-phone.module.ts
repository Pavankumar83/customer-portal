import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CustomerPortalSharedModule } from 'app/shared/shared.module';
import { CustomerPhoneComponent } from './customer-phone.component';
import { CustomerPhoneDetailComponent } from './customer-phone-detail.component';
import { CustomerPhoneUpdateComponent } from './customer-phone-update.component';
import { CustomerPhoneDeleteDialogComponent } from './customer-phone-delete-dialog.component';
import { customerPhoneRoute } from './customer-phone.route';

@NgModule({
  imports: [CustomerPortalSharedModule, RouterModule.forChild(customerPhoneRoute)],
  declarations: [CustomerPhoneComponent, CustomerPhoneDetailComponent, CustomerPhoneUpdateComponent, CustomerPhoneDeleteDialogComponent],
  entryComponents: [CustomerPhoneDeleteDialogComponent],
})
export class CustomerPortalCustomerPhoneModule {}
