import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IMaker } from 'app/shared/model/maker.model';

type EntityResponseType = HttpResponse<IMaker>;
type EntityArrayResponseType = HttpResponse<IMaker[]>;

@Injectable({ providedIn: 'root' })
export class MakerService {
  public resourceUrl = SERVER_API_URL + 'api/makers';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/makers';

  constructor(protected http: HttpClient) {}

  create(maker: IMaker): Observable<EntityResponseType> {
    return this.http.post<IMaker>(this.resourceUrl, maker, { observe: 'response' });
  }

  update(maker: IMaker): Observable<EntityResponseType> {
    return this.http.put<IMaker>(this.resourceUrl, maker, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMaker>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMaker[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMaker[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
