import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CustomerPortalSharedModule } from 'app/shared/shared.module';
import { BankInfoComponent } from './bank-info.component';
import { BankInfoDetailComponent } from './bank-info-detail.component';
import { BankInfoUpdateComponent } from './bank-info-update.component';
import { BankInfoDeleteDialogComponent } from './bank-info-delete-dialog.component';
import { bankInfoRoute } from './bank-info.route';

@NgModule({
  imports: [CustomerPortalSharedModule, RouterModule.forChild(bankInfoRoute)],
  declarations: [BankInfoComponent, BankInfoDetailComponent, BankInfoUpdateComponent, BankInfoDeleteDialogComponent],
  entryComponents: [BankInfoDeleteDialogComponent],
})
export class CustomerPortalBankInfoModule {}
