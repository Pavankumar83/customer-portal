import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CustomerPortalSharedModule } from 'app/shared/shared.module';
import { CustomerAddressComponent } from './customer-address.component';
import { CustomerAddressDetailComponent } from './customer-address-detail.component';
import { CustomerAddressUpdateComponent } from './customer-address-update.component';
import { CustomerAddressDeleteDialogComponent } from './customer-address-delete-dialog.component';
import { customerAddressRoute } from './customer-address.route';

@NgModule({
  imports: [CustomerPortalSharedModule, RouterModule.forChild(customerAddressRoute)],
  declarations: [
    CustomerAddressComponent,
    CustomerAddressDetailComponent,
    CustomerAddressUpdateComponent,
    CustomerAddressDeleteDialogComponent,
  ],
  entryComponents: [CustomerAddressDeleteDialogComponent],
})
export class CustomerPortalCustomerAddressModule {}
