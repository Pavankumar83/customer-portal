import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILegalEntity } from 'app/shared/model/legal-entity.model';
import { LegalEntityService } from './legal-entity.service';
import { LegalEntityDeleteDialogComponent } from './legal-entity-delete-dialog.component';

@Component({
  selector: 'jhi-legal-entity',
  templateUrl: './legal-entity.component.html',
})
export class LegalEntityComponent implements OnInit, OnDestroy {
  legalEntities?: ILegalEntity[];
  eventSubscriber?: Subscription;

  constructor(
    protected legalEntityService: LegalEntityService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.legalEntityService.query().subscribe((res: HttpResponse<ILegalEntity[]>) => (this.legalEntities = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLegalEntities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILegalEntity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLegalEntities(): void {
    this.eventSubscriber = this.eventManager.subscribe('legalEntityListModification', () => this.loadAll());
  }

  delete(legalEntity: ILegalEntity): void {
    const modalRef = this.modalService.open(LegalEntityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.legalEntity = legalEntity;
  }
}
