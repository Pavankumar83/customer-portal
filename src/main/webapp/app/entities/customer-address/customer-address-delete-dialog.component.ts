import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerAddress } from 'app/shared/model/customer-address.model';
import { CustomerAddressService } from './customer-address.service';

@Component({
  templateUrl: './customer-address-delete-dialog.component.html',
})
export class CustomerAddressDeleteDialogComponent {
  customerAddress?: ICustomerAddress;

  constructor(
    protected customerAddressService: CustomerAddressService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customerAddressService.delete(id).subscribe(() => {
      this.eventManager.broadcast('customerAddressListModification');
      this.activeModal.close();
    });
  }
}
