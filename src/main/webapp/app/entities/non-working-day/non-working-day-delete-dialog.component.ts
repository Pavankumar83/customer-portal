import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INonWorkingDay } from 'app/shared/model/non-working-day.model';
import { NonWorkingDayService } from './non-working-day.service';

@Component({
  templateUrl: './non-working-day-delete-dialog.component.html',
})
export class NonWorkingDayDeleteDialogComponent {
  nonWorkingDay?: INonWorkingDay;

  constructor(
    protected nonWorkingDayService: NonWorkingDayService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nonWorkingDayService.delete(id).subscribe(() => {
      this.eventManager.broadcast('nonWorkingDayListModification');
      this.activeModal.close();
    });
  }
}
