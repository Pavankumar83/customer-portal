import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICustomerPhone } from 'app/shared/model/customer-phone.model';

type EntityResponseType = HttpResponse<ICustomerPhone>;
type EntityArrayResponseType = HttpResponse<ICustomerPhone[]>;

@Injectable({ providedIn: 'root' })
export class CustomerPhoneService {
  public resourceUrl = SERVER_API_URL + 'api/customer-phones';

  constructor(protected http: HttpClient) {}

  create(customerPhone: ICustomerPhone): Observable<EntityResponseType> {
    return this.http.post<ICustomerPhone>(this.resourceUrl, customerPhone, { observe: 'response' });
  }

  update(customerPhone: ICustomerPhone): Observable<EntityResponseType> {
    return this.http.put<ICustomerPhone>(this.resourceUrl, customerPhone, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICustomerPhone>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomerPhone[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
