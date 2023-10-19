import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPatient, NewPatient } from '../patient.model';
import { Search } from 'app/core/request/request.model';

export type PartialUpdatePatient = Partial<IPatient> & Pick<IPatient, 'patientId'>;

export type EntityResponseType = HttpResponse<IPatient>;
export type EntityArrayResponseType = HttpResponse<IPatient[]>;

@Injectable({ providedIn: 'root' })
export class PatientService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/patients');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(patient: NewPatient): Observable<EntityResponseType> {
    return this.http.post<IPatient>(this.resourceUrl, patient, { observe: 'response' });
  }

  update(patient: IPatient): Observable<EntityResponseType> {
    return this.http.put<IPatient>(`${this.resourceUrl}/${this.getPatientIdentifier(patient)}`, patient, {
      observe: 'response',
    });
  }

  partialUpdate(patient: PartialUpdatePatient): Observable<EntityResponseType> {
    return this.http.patch<IPatient>(`${this.resourceUrl}/${this.getPatientIdentifier(patient)}`, patient, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IPatient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPatient[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPatientIdentifier(patient: Pick<IPatient, 'patientId'>): string {
    return patient.patientId;
  }

  comparePatient(o1: Pick<IPatient, 'patientId'> | null, o2: Pick<IPatient, 'patientId'> | null): boolean {
    return o1 && o2 ? this.getPatientIdentifier(o1) === this.getPatientIdentifier(o2) : o1 === o2;
  }

  searchPatient(req?: Search): Observable<HttpResponse<IPatient[]>> {
    const options = createRequestOption(req);
    return this.http.get<IPatient[]>(`${this.resourceUrl}/search`, { params: options, observe: 'response' });
  }

  addPatientToCollectionIfMissing<Type extends Pick<IPatient, 'patientId'>>(
    patientCollection: Type[],
    ...patientsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const patients: Type[] = patientsToCheck.filter(isPresent);
    if (patients.length > 0) {
      const patientCollectionIdentifiers = patientCollection.map(patientItem => this.getPatientIdentifier(patientItem)!);
      const patientsToAdd = patients.filter(patientItem => {
        const patientIdentifier = this.getPatientIdentifier(patientItem);
        if (patientCollectionIdentifiers.includes(patientIdentifier)) {
          return false;
        }
        patientCollectionIdentifiers.push(patientIdentifier);
        return true;
      });
      return [...patientsToAdd, ...patientCollection];
    }
    return patientCollection;
  }
}
