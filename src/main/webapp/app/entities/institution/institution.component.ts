import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInstitution } from 'app/shared/model/institution.model';
import { InstitutionService } from './institution.service';
import { InstitutionDeleteDialogComponent } from './institution-delete-dialog.component';

@Component({
  selector: 'jhi-institution',
  templateUrl: './institution.component.html',
})
export class InstitutionComponent implements OnInit, OnDestroy {
  institutions?: IInstitution[];
  eventSubscriber?: Subscription;

  constructor(
    protected institutionService: InstitutionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.institutionService.query().subscribe((res: HttpResponse<IInstitution[]>) => (this.institutions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInstitutions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInstitution): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInstitutions(): void {
    this.eventSubscriber = this.eventManager.subscribe('institutionListModification', () => this.loadAll());
  }

  delete(institution: IInstitution): void {
    const modalRef = this.modalService.open(InstitutionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.institution = institution;
  }
}
