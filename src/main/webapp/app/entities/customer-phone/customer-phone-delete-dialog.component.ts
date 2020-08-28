import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerPhone } from 'app/shared/model/customer-phone.model';
import { CustomerPhoneService } from './customer-phone.service';

@Component({
  templateUrl: './customer-phone-delete-dialog.component.html',
})
export class CustomerPhoneDeleteDialogComponent {
  customerPhone?: ICustomerPhone;

  constructor(
    protected customerPhoneService: CustomerPhoneService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customerPhoneService.delete(id).subscribe(() => {
      this.eventManager.broadcast('customerPhoneListModification');
      this.activeModal.close();
    });
  }
}
