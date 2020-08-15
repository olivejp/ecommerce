import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IAttribut } from 'app/shared/model/attribut.model';

type EntityResponseType = HttpResponse<IAttribut>;
type EntityArrayResponseType = HttpResponse<IAttribut[]>;

@Injectable({ providedIn: 'root' })
export class AttributService {
  public resourceUrl = SERVER_API_URL + 'api/attributs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/attributs';

  constructor(protected http: HttpClient) {}

  create(attribut: IAttribut): Observable<EntityResponseType> {
    return this.http.post<IAttribut>(this.resourceUrl, attribut, { observe: 'response' });
  }

  update(attribut: IAttribut): Observable<EntityResponseType> {
    return this.http.put<IAttribut>(this.resourceUrl, attribut, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAttribut>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAttribut[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAttribut[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
