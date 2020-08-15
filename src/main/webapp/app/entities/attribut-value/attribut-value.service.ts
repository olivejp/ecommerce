import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IAttributValue } from 'app/shared/model/attribut-value.model';

type EntityResponseType = HttpResponse<IAttributValue>;
type EntityArrayResponseType = HttpResponse<IAttributValue[]>;

@Injectable({ providedIn: 'root' })
export class AttributValueService {
  public resourceUrl = SERVER_API_URL + 'api/attribut-values';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/attribut-values';

  constructor(protected http: HttpClient) {}

  create(attributValue: IAttributValue): Observable<EntityResponseType> {
    return this.http.post<IAttributValue>(this.resourceUrl, attributValue, { observe: 'response' });
  }

  update(attributValue: IAttributValue): Observable<EntityResponseType> {
    return this.http.put<IAttributValue>(this.resourceUrl, attributValue, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAttributValue>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAttributValue[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAttributValue[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
