import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CustomerPortalSharedModule } from 'app/shared/shared.module';
import { OrderProductComponent } from './order-product.component';
import { OrderProductDetailComponent } from './order-product-detail.component';
import { OrderProductUpdateComponent } from './order-product-update.component';
import { OrderProductDeleteDialogComponent } from './order-product-delete-dialog.component';
import { orderProductRoute } from './order-product.route';

@NgModule({
  imports: [CustomerPortalSharedModule, RouterModule.forChild(orderProductRoute)],
  declarations: [OrderProductComponent, OrderProductDetailComponent, OrderProductUpdateComponent, OrderProductDeleteDialogComponent],
  entryComponents: [OrderProductDeleteDialogComponent],
})
export class CustomerPortalOrderProductModule {}
