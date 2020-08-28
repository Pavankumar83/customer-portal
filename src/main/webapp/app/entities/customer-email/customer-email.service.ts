import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICustomerEmail } from 'app/shared/model/customer-email.model';

type EntityResponseType = HttpResponse<ICustomerEmail>;
type EntityArrayResponseType = HttpResponse<ICustomerEmail[]>;

@Injectable({ providedIn: 'root' })
export class CustomerEmailService {
  public resourceUrl = SERVER_API_URL + 'api/customer-emails';

  constructor(protected http: HttpClient) {}

  create(customerEmail: ICustomerEmail): Observable<EntityResponseType> {
    return this.http.post<ICustomerEmail>(this.resourceUrl, customerEmail, { observe: 'response' });
  }

  update(customerEmail: ICustomerEmail): Observable<EntityResponseType> {
    return this.http.put<ICustomerEmail>(this.resourceUrl, customerEmail, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICustomerEmail>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomerEmail[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
