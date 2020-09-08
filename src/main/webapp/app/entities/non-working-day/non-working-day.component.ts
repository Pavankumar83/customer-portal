import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INonWorkingDay } from 'app/shared/model/non-working-day.model';
import { NonWorkingDayService } from './non-working-day.service';
import { NonWorkingDayDeleteDialogComponent } from './non-working-day-delete-dialog.component';

@Component({
  selector: 'jhi-non-working-day',
  templateUrl: './non-working-day.component.html',
})
export class NonWorkingDayComponent implements OnInit, OnDestroy {
  nonWorkingDays?: INonWorkingDay[];
  eventSubscriber?: Subscription;

  constructor(
    protected nonWorkingDayService: NonWorkingDayService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.nonWorkingDayService.query().subscribe((res: HttpResponse<INonWorkingDay[]>) => (this.nonWorkingDays = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNonWorkingDays();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INonWorkingDay): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInNonWorkingDays(): void {
    this.eventSubscriber = this.eventManager.subscribe('nonWorkingDayListModification', () => this.loadAll());
  }

  delete(nonWorkingDay: INonWorkingDay): void {
    const modalRef = this.modalService.open(NonWorkingDayDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nonWorkingDay = nonWorkingDay;
  }
}
