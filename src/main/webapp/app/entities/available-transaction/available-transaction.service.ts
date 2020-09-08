import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAvailableTransaction } from 'app/shared/model/available-transaction.model';

type EntityResponseType = HttpResponse<IAvailableTransaction>;
type EntityArrayResponseType = HttpResponse<IAvailableTransaction[]>;

@Injectable({ providedIn: 'root' })
export class AvailableTransactionService {
  public resourceUrl = SERVER_API_URL + 'api/available-transactions';

  constructor(protected http: HttpClient) {}

  create(availableTransaction: IAvailableTransaction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(availableTransaction);
    return this.http
      .post<IAvailableTransaction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(availableTransaction: IAvailableTransaction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(availableTransaction);
    return this.http
      .put<IAvailableTransaction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAvailableTransaction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAvailableTransaction[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(availableTransaction: IAvailableTransaction): IAvailableTransaction {
    const copy: IAvailableTransaction = Object.assign({}, availableTransaction, {
      dateOfTransaction:
        availableTransaction.dateOfTransaction && availableTransaction.dateOfTransaction.isValid()
          ? availableTransaction.dateOfTransaction.toJSON()
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfTransaction = res.body.dateOfTransaction ? moment(res.body.dateOfTransaction) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((availableTransaction: IAvailableTransaction) => {
        availableTransaction.dateOfTransaction = availableTransaction.dateOfTransaction
          ? moment(availableTransaction.dateOfTransaction)
          : undefined;
      });
    }
    return res;
  }
}
