import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBankInfo } from 'app/shared/model/bank-info.model';
import { BankInfoService } from './bank-info.service';

@Component({
  templateUrl: './bank-info-delete-dialog.component.html',
})
export class BankInfoDeleteDialogComponent {
  bankInfo?: IBankInfo;

  constructor(protected bankInfoService: BankInfoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bankInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bankInfoListModification');
      this.activeModal.close();
    });
  }
}
