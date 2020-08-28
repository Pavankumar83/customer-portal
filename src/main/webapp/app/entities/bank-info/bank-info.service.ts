import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBankInfo } from 'app/shared/model/bank-info.model';

type EntityResponseType = HttpResponse<IBankInfo>;
type EntityArrayResponseType = HttpResponse<IBankInfo[]>;

@Injectable({ providedIn: 'root' })
export class BankInfoService {
  public resourceUrl = SERVER_API_URL + 'api/bank-infos';

  constructor(protected http: HttpClient) {}

  create(bankInfo: IBankInfo): Observable<EntityResponseType> {
    return this.http.post<IBankInfo>(this.resourceUrl, bankInfo, { observe: 'response' });
  }

  update(bankInfo: IBankInfo): Observable<EntityResponseType> {
    return this.http.put<IBankInfo>(this.resourceUrl, bankInfo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBankInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBankInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
