import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CustomerPortalSharedModule } from 'app/shared/shared.module';
import { NonWorkingDayComponent } from './non-working-day.component';
import { NonWorkingDayDetailComponent } from './non-working-day-detail.component';
import { NonWorkingDayUpdateComponent } from './non-working-day-update.component';
import { NonWorkingDayDeleteDialogComponent } from './non-working-day-delete-dialog.component';
import { nonWorkingDayRoute } from './non-working-day.route';

@NgModule({
  imports: [CustomerPortalSharedModule, RouterModule.forChild(nonWorkingDayRoute)],
  declarations: [NonWorkingDayComponent, NonWorkingDayDetailComponent, NonWorkingDayUpdateComponent, NonWorkingDayDeleteDialogComponent],
  entryComponents: [NonWorkingDayDeleteDialogComponent],
})
export class CustomerPortalNonWorkingDayModule {}
