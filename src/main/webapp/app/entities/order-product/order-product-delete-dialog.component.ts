import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderProduct } from 'app/shared/model/order-product.model';
import { OrderProductService } from './order-product.service';

@Component({
  templateUrl: './order-product-delete-dialog.component.html',
})
export class OrderProductDeleteDialogComponent {
  orderProduct?: IOrderProduct;

  constructor(
    protected orderProductService: OrderProductService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderProductService.delete(id).subscribe(() => {
      this.eventManager.broadcast('orderProductListModification');
      this.activeModal.close();
    });
  }
}
