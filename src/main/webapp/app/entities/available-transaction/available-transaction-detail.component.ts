import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAvailableTransaction } from 'app/shared/model/available-transaction.model';

@Component({
  selector: 'jhi-available-transaction-detail',
  templateUrl: './available-transaction-detail.component.html',
})
export class AvailableTransactionDetailComponent implements OnInit {
  availableTransaction: IAvailableTransaction | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ availableTransaction }) => (this.availableTransaction = availableTransaction));
  }

  previousState(): void {
    window.history.back();
  }
}
