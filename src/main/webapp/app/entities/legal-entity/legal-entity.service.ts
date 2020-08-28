import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILegalEntity } from 'app/shared/model/legal-entity.model';

type EntityResponseType = HttpResponse<ILegalEntity>;
type EntityArrayResponseType = HttpResponse<ILegalEntity[]>;

@Injectable({ providedIn: 'root' })
export class LegalEntityService {
  public resourceUrl = SERVER_API_URL + 'api/legal-entities';

  constructor(protected http: HttpClient) {}

  create(legalEntity: ILegalEntity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(legalEntity);
    return this.http
      .post<ILegalEntity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(legalEntity: ILegalEntity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(legalEntity);
    return this.http
      .put<ILegalEntity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILegalEntity>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILegalEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(legalEntity: ILegalEntity): ILegalEntity {
    const copy: ILegalEntity = Object.assign({}, legalEntity, {
      dateOfStart: legalEntity.dateOfStart && legalEntity.dateOfStart.isValid() ? legalEntity.dateOfStart.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfStart = res.body.dateOfStart ? moment(res.body.dateOfStart) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((legalEntity: ILegalEntity) => {
        legalEntity.dateOfStart = legalEntity.dateOfStart ? moment(legalEntity.dateOfStart) : undefined;
      });
    }
    return res;
  }
}
