import{Component, OnInit, OnDestroy}from '@angular/core';
import {HttpResponse}from '@angular/common/http';
import { Subscription}from 'rxjs';
import { JhiEventManager}from 'ng-jhipster';
import { NgbModal}from '@ng-bootstrap/ng-bootstrap';

import {IAvailableTransaction}from 'app/shared/model/available-transaction.model';
import {AvailableTransactionService}from './available-transaction.service';
import {AvailableTransactionDeleteDialogComponent}from './available-transaction-delete-dialog.component';

@Component({
selector: 'jhi-available-transaction',
templateUrl: './available-transaction.component.html',
})
export class AvailableTransactionComponent implements OnInit, OnDestroy {
availableTransactions?: IAvailableTransaction[];
eventSubscriber?: Subscription;

constructor(
    protected availableTransactionService: AvailableTransactionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.availableTransactionService
      .query()
      .subscribe((res: HttpResponse<IAvailableTransaction[]>) =>{
       this.availableTransactions = res.body || []
});
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAvailableTransactions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAvailableTransaction): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAvailableTransactions(): void {
    this.eventSubscriber = this.eventManager.subscribe('availableTransactionListModification', () => this.loadAll());
  }

  delete(availableTransaction: IAvailableTransaction): void {
    const modalRef = this.modalService.open(AvailableTransactionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.availableTransaction = availableTransaction;
  }
}
