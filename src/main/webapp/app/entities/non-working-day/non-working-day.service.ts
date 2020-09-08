import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INonWorkingDay } from 'app/shared/model/non-working-day.model';

type EntityResponseType = HttpResponse<INonWorkingDay>;
type EntityArrayResponseType = HttpResponse<INonWorkingDay[]>;

@Injectable({ providedIn: 'root' })
export class NonWorkingDayService {
  public resourceUrl = SERVER_API_URL + 'api/non-working-days';

  constructor(protected http: HttpClient) {}

  create(nonWorkingDay: INonWorkingDay): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nonWorkingDay);
    return this.http
      .post<INonWorkingDay>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(nonWorkingDay: INonWorkingDay): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nonWorkingDay);
    return this.http
      .put<INonWorkingDay>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INonWorkingDay>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INonWorkingDay[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(nonWorkingDay: INonWorkingDay): INonWorkingDay {
    const copy: INonWorkingDay = Object.assign({}, nonWorkingDay, {
      nonWorkingDay:
        nonWorkingDay.nonWorkingDay && nonWorkingDay.nonWorkingDay.isValid() ? nonWorkingDay.nonWorkingDay.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.nonWorkingDay = res.body.nonWorkingDay ? moment(res.body.nonWorkingDay) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((nonWorkingDay: INonWorkingDay) => {
        nonWorkingDay.nonWorkingDay = nonWorkingDay.nonWorkingDay ? moment(nonWorkingDay.nonWorkingDay) : undefined;
      });
    }
    return res;
  }
}
