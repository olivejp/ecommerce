import {Injectable, OnDestroy} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';

import {SERVER_API_URL} from 'app/app.constants';
import {createRequestOption, Search} from 'app/shared/util/request-util';
import {ICategory} from 'app/shared/model/category.model';

type EntityResponseType = HttpResponse<ICategory>;
type EntityArrayResponseType = HttpResponse<ICategory[]>;

export enum TypeCategory {
  CREATED,
  UPDATED,
  RELAOAD_ALL
}

@Injectable({providedIn: 'root'})
export class CategoryService implements OnDestroy {
  public resourceUrl = SERVER_API_URL + 'api/categories';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/categories';
  private categoryReload = new Subject<[TypeCategory, ICategory | null | undefined]>()

  constructor(protected http: HttpClient) {
  }

  ngOnDestroy(): void {
    if (this.categoryReload) {
      this.categoryReload.complete();
      this.categoryReload.unsubscribe();
    }
  }

  create(category: ICategory): Observable<EntityResponseType> {
    return this.http.post<ICategory>(this.resourceUrl, category, {observe: 'response'});
  }

  update(category: ICategory): Observable<EntityResponseType> {
    return this.http.put<ICategory>(this.resourceUrl, category, {observe: 'response'});
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategory>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategory[]>(this.resourceUrl, {params: options, observe: 'response'});
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategory[]>(this.resourceSearchUrl, {params: options, observe: 'response'});
  }

  reindex(): Observable<any> {
    return this.http.post(`${this.resourceSearchUrl}/reindex`, {observe: 'response'});
  }

  emit(subject: TypeCategory, category: ICategory | null | undefined): void {
    this.categoryReload.next([subject, category]);
  }

  listen(): Observable<[TypeCategory, ICategory | null | undefined]> {
    return this.categoryReload;
  }
}
