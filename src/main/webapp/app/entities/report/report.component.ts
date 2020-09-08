import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReport } from 'app/shared/model/report.model';
import { ReportService } from './report.service';
import { ReportDeleteDialogComponent } from './report-delete-dialog.component';

@Component({
  selector: 'jhi-report',
  templateUrl: './report.component.html',
})
export class ReportComponent implements OnInit, OnDestroy {
  reports?: IReport[];
  eventSubscriber?: Subscription;

  constructor(protected reportService: ReportService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.reportService.query().subscribe((res: HttpResponse<IReport[]>) => (this.reports = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInReports();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IReport): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInReports(): void {
    this.eventSubscriber = this.eventManager.subscribe('reportListModification', () => this.loadAll());
  }

  delete(report: IReport): void {
    const modalRef = this.modalService.open(ReportDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.report = report;
  }
}
