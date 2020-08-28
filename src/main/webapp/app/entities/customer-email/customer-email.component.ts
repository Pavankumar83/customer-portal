import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomerEmail } from 'app/shared/model/customer-email.model';
import { CustomerEmailService } from './customer-email.service';
import { CustomerEmailDeleteDialogComponent } from './customer-email-delete-dialog.component';

@Component({
  selector: 'jhi-customer-email',
  templateUrl: './customer-email.component.html',
})
export class CustomerEmailComponent implements OnInit, OnDestroy {
  customerEmails?: ICustomerEmail[];
  eventSubscriber?: Subscription;

  constructor(
    protected customerEmailService: CustomerEmailService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.customerEmailService.query().subscribe((res: HttpResponse<ICustomerEmail[]>) => (this.customerEmails = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCustomerEmails();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICustomerEmail): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCustomerEmails(): void {
    this.eventSubscriber = this.eventManager.subscribe('customerEmailListModification', () => this.loadAll());
  }

  delete(customerEmail: ICustomerEmail): void {
    const modalRef = this.modalService.open(CustomerEmailDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.customerEmail = customerEmail;
  }
}
