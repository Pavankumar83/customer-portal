import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INonWorkingDay } from 'app/shared/model/non-working-day.model';

@Component({
  selector: 'jhi-non-working-day-detail',
  templateUrl: './non-working-day-detail.component.html',
})
export class NonWorkingDayDetailComponent implements OnInit {
  nonWorkingDay: INonWorkingDay | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nonWorkingDay }) => (this.nonWorkingDay = nonWorkingDay));
  }

  previousState(): void {
    window.history.back();
  }
}
