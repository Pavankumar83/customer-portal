import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAvailableTransaction } from 'app/shared/model/available-transaction.model';
import { AvailableTransactionService } from './available-transaction.service';

@Component({
  templateUrl: './available-transaction-delete-dialog.component.html',
})
export class AvailableTransactionDeleteDialogComponent {
  availableTransaction?: IAvailableTransaction;

  constructor(
    protected availableTransactionService: AvailableTransactionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.availableTransactionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('availableTransactionListModification');
      this.activeModal.close();
    });
  }
}
