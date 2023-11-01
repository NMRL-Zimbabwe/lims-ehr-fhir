import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { IDashboardSummary } from './dashboard.model';

export type EntityResponseType = HttpResponse<IDashboardSummary>;
export type EntityArrayResponseType = HttpResponse<IDashboardSummary[]>;

@Injectable({ providedIn: 'root' })
export class DashboardSummaryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dashboard/count');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  query(): Observable<EntityResponseType> {
    return this.http.get<IDashboardSummary>(this.resourceUrl, { observe: 'response' });
  }
}
