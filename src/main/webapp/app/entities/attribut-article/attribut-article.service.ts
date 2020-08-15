import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IAttributArticle } from 'app/shared/model/attribut-article.model';

type EntityResponseType = HttpResponse<IAttributArticle>;
type EntityArrayResponseType = HttpResponse<IAttributArticle[]>;

@Injectable({ providedIn: 'root' })
export class AttributArticleService {
  public resourceUrl = SERVER_API_URL + 'api/attribut-articles';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/attribut-articles';

  constructor(protected http: HttpClient) {}

  create(attributArticle: IAttributArticle): Observable<EntityResponseType> {
    return this.http.post<IAttributArticle>(this.resourceUrl, attributArticle, { observe: 'response' });
  }

  update(attributArticle: IAttributArticle): Observable<EntityResponseType> {
    return this.http.put<IAttributArticle>(this.resourceUrl, attributArticle, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAttributArticle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAttributArticle[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAttributArticle[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
