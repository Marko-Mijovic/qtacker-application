import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ICompanyExtern } from 'app/shared/model/company-extern.model';

type EntityResponseType = HttpResponse<ICompanyExtern>;
type EntityArrayResponseType = HttpResponse<ICompanyExtern[]>;

@Injectable({ providedIn: 'root' })
export class CompanyExternService {
  public resourceUrl = SERVER_API_URL + 'api/company-externs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/company-externs';

  constructor(protected http: HttpClient) {}

  create(companyExtern: ICompanyExtern): Observable<EntityResponseType> {
    return this.http.post<ICompanyExtern>(this.resourceUrl, companyExtern, { observe: 'response' });
  }

  update(companyExtern: ICompanyExtern): Observable<EntityResponseType> {
    return this.http.put<ICompanyExtern>(this.resourceUrl, companyExtern, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompanyExtern>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompanyExtern[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompanyExtern[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
