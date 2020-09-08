import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrderProduct } from 'app/shared/model/order-product.model';
import { OrderProductService } from './order-product.service';
import { OrderProductDeleteDialogComponent } from './order-product-delete-dialog.component';

@Component({
  selector: 'jhi-order-product',
  templateUrl: './order-product.component.html',
})
export class OrderProductComponent implements OnInit, OnDestroy {
  orderProducts?: IOrderProduct[];
  eventSubscriber?: Subscription;

  constructor(
    protected orderProductService: OrderProductService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.orderProductService.query().subscribe((res: HttpResponse<IOrderProduct[]>) => (this.orderProducts = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOrderProducts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOrderProduct): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOrderProducts(): void {
    this.eventSubscriber = this.eventManager.subscribe('orderProductListModification', () => this.loadAll());
  }

  delete(orderProduct: IOrderProduct): void {
    const modalRef = this.modalService.open(OrderProductDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.orderProduct = orderProduct;
  }
}
