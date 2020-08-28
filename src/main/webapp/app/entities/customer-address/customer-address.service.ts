import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICustomerAddress } from 'app/shared/model/customer-address.model';

type EntityResponseType = HttpResponse<ICustomerAddress>;
type EntityArrayResponseType = HttpResponse<ICustomerAddress[]>;

@Injectable({ providedIn: 'root' })
export class CustomerAddressService {
  public resourceUrl = SERVER_API_URL + 'api/customer-addresses';

  constructor(protected http: HttpClient) {}

  create(customerAddress: ICustomerAddress): Observable<EntityResponseType> {
    return this.http.post<ICustomerAddress>(this.resourceUrl, customerAddress, { observe: 'response' });
  }

  update(customerAddress: ICustomerAddress): Observable<EntityResponseType> {
    return this.http.put<ICustomerAddress>(this.resourceUrl, customerAddress, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICustomerAddress>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomerAddress[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
