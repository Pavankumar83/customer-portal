import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBankInfo } from 'app/shared/model/bank-info.model';
import { BankInfoService } from './bank-info.service';
import { BankInfoDeleteDialogComponent } from './bank-info-delete-dialog.component';

@Component({
  selector: 'jhi-bank-info',
  templateUrl: './bank-info.component.html',
})
export class BankInfoComponent implements OnInit, OnDestroy {
  bankInfos?: IBankInfo[];
  eventSubscriber?: Subscription;

  constructor(protected bankInfoService: BankInfoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.bankInfoService.query().subscribe((res: HttpResponse<IBankInfo[]>) => (this.bankInfos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBankInfos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBankInfo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBankInfos(): void {
    this.eventSubscriber = this.eventManager.subscribe('bankInfoListModification', () => this.loadAll());
  }

  delete(bankInfo: IBankInfo): void {
    const modalRef = this.modalService.open(BankInfoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bankInfo = bankInfo;
  }
}
