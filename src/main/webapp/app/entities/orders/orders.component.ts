import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrders } from 'app/shared/model/orders.model';
import { OrdersService } from './orders.service';
import { OrdersDeleteDialogComponent } from './orders-delete-dialog.component';

@Component({
  selector: 'jhi-orders',
  templateUrl: './orders.component.html',
})
export class OrdersComponent implements OnInit, OnDestroy {
  orders?: IOrders[];
  eventSubscriber?: Subscription;

  constructor(protected ordersService: OrdersService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.ordersService.query().subscribe((res: HttpResponse<IOrders[]>) => (this.orders = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOrders();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOrders): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOrders(): void {
    this.eventSubscriber = this.eventManager.subscribe('ordersListModification', () => this.loadAll());
  }

  delete(orders: IOrders): void {
    const modalRef = this.modalService.open(OrdersDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.orders = orders;
  }
}
