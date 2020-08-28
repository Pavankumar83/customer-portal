import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomerPhone } from 'app/shared/model/customer-phone.model';
import { CustomerPhoneService } from './customer-phone.service';
import { CustomerPhoneDeleteDialogComponent } from './customer-phone-delete-dialog.component';

@Component({
  selector: 'jhi-customer-phone',
  templateUrl: './customer-phone.component.html',
})
export class CustomerPhoneComponent implements OnInit, OnDestroy {
  customerPhones?: ICustomerPhone[];
  eventSubscriber?: Subscription;

  constructor(
    protected customerPhoneService: CustomerPhoneService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.customerPhoneService.query().subscribe((res: HttpResponse<ICustomerPhone[]>) => (this.customerPhones = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCustomerPhones();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICustomerPhone): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCustomerPhones(): void {
    this.eventSubscriber = this.eventManager.subscribe('customerPhoneListModification', () => this.loadAll());
  }

  delete(customerPhone: ICustomerPhone): void {
    const modalRef = this.modalService.open(CustomerPhoneDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.customerPhone = customerPhone;
  }
}
