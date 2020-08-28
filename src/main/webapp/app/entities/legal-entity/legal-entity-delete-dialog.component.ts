import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILegalEntity } from 'app/shared/model/legal-entity.model';
import { LegalEntityService } from './legal-entity.service';

@Component({
  templateUrl: './legal-entity-delete-dialog.component.html',
})
export class LegalEntityDeleteDialogComponent {
  legalEntity?: ILegalEntity;

  constructor(
    protected legalEntityService: LegalEntityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.legalEntityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('legalEntityListModification');
      this.activeModal.close();
    });
  }
}
