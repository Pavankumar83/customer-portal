import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerEmail } from 'app/shared/model/customer-email.model';
import { CustomerEmailService } from './customer-email.service';

@Component({
  templateUrl: './customer-email-delete-dialog.component.html',
})
export class CustomerEmailDeleteDialogComponent {
  customerEmail?: ICustomerEmail;

  constructor(
    protected customerEmailService: CustomerEmailService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customerEmailService.delete(id).subscribe(() => {
      this.eventManager.broadcast('customerEmailListModification');
      this.activeModal.close();
    });
  }
}
