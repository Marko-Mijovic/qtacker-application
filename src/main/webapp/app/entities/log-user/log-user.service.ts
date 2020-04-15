import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ILogUser } from 'app/shared/model/log-user.model';

type EntityResponseType = HttpResponse<ILogUser>;
type EntityArrayResponseType = HttpResponse<ILogUser[]>;

@Injectable({ providedIn: 'root' })
export class LogUserService {
  public resourceUrl = SERVER_API_URL + 'api/log-users';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/log-users';

  constructor(protected http: HttpClient) {}

  create(logUser: ILogUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(logUser);
    return this.http
      .post<ILogUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(logUser: ILogUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(logUser);
    return this.http
      .put<ILogUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILogUser>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILogUser[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILogUser[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(logUser: ILogUser): ILogUser {
    const copy: ILogUser = Object.assign({}, logUser, {
      dateTimeLog: logUser.dateTimeLog && logUser.dateTimeLog.isValid() ? logUser.dateTimeLog.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateTimeLog = res.body.dateTimeLog ? moment(res.body.dateTimeLog) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((logUser: ILogUser) => {
        logUser.dateTimeLog = logUser.dateTimeLog ? moment(logUser.dateTimeLog) : undefined;
      });
    }
    return res;
  }
}
