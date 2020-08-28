import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomerAddress } from 'app/shared/model/customer-address.model';
import { CustomerAddressService } from './customer-address.service';
import { CustomerAddressDeleteDialogComponent } from './customer-address-delete-dialog.component';

@Component({
  selector: 'jhi-customer-address',
  templateUrl: './customer-address.component.html',
})
export class CustomerAddressComponent implements OnInit, OnDestroy {
  customerAddresses?: ICustomerAddress[];
  eventSubscriber?: Subscription;

  constructor(
    protected customerAddressService: CustomerAddressService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.customerAddressService.query().subscribe((res: HttpResponse<ICustomerAddress[]>) => (this.customerAddresses = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCustomerAddresses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICustomerAddress): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCustomerAddresses(): void {
    this.eventSubscriber = this.eventManager.subscribe('customerAddressListModification', () => this.loadAll());
  }

  delete(customerAddress: ICustomerAddress): void {
    const modalRef = this.modalService.open(CustomerAddressDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.customerAddress = customerAddress;
  }
}
