import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBankInfo } from 'app/shared/model/bank-info.model';

@Component({
  selector: 'jhi-bank-info-detail',
  templateUrl: './bank-info-detail.component.html',
})
export class BankInfoDetailComponent implements OnInit {
  bankInfo: IBankInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bankInfo }) => (this.bankInfo = bankInfo));
  }

  previousState(): void {
    window.history.back();
  }
}
