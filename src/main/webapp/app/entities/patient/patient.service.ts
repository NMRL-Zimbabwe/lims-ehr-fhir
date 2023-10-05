import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { Observable } from 'rxjs';
import { IPatient } from './patient.model';

@Injectable({
  providedIn: 'root',
})
export class PatientService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/patients');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  query(req?: Pagination): Observable<HttpResponse<IPatient[]>> {
    const options = createRequestOption(req);
    return this.http.get<IPatient[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
