import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IServiceIntervention } from 'app/shared/model/service-intervention.model';

type EntityResponseType = HttpResponse<IServiceIntervention>;
type EntityArrayResponseType = HttpResponse<IServiceIntervention[]>;

@Injectable({ providedIn: 'root' })
export class ServiceInterventionService {
  public resourceUrl = SERVER_API_URL + 'api/service-interventions';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/service-interventions';

  constructor(protected http: HttpClient) {}

  create(serviceIntervention: IServiceIntervention): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceIntervention);
    return this.http
      .post<IServiceIntervention>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceIntervention: IServiceIntervention): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceIntervention);
    return this.http
      .put<IServiceIntervention>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceIntervention>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceIntervention[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceIntervention[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(serviceIntervention: IServiceIntervention): IServiceIntervention {
    const copy: IServiceIntervention = Object.assign({}, serviceIntervention, {
      dateTime: serviceIntervention.dateTime && serviceIntervention.dateTime.isValid() ? serviceIntervention.dateTime.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateTime = res.body.dateTime ? moment(res.body.dateTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((serviceIntervention: IServiceIntervention) => {
        serviceIntervention.dateTime = serviceIntervention.dateTime ? moment(serviceIntervention.dateTime) : undefined;
      });
    }
    return res;
  }
}
