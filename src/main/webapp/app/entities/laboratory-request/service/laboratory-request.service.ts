import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILaboratoryRequest, NewLaboratoryRequest } from '../laboratory-request.model';
import { Search } from 'app/core/request/request.model';

export type PartialUpdateDeveloper = Partial<ILaboratoryRequest> & Pick<ILaboratoryRequest, 'laboratoryRequestId'>;

export type EntityResponseType = HttpResponse<ILaboratoryRequest>;
export type EntityArrayResponseType = HttpResponse<ILaboratoryRequest[]>;

@Injectable({ providedIn: 'root' })
export class LaboratoryRequestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/laboratoryRequests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(laboratoryRequest: NewLaboratoryRequest): Observable<EntityResponseType> {
    return this.http.post<ILaboratoryRequest>(this.resourceUrl, laboratoryRequest, { observe: 'response' });
  }

  update(laboratoryRequest: ILaboratoryRequest): Observable<EntityResponseType> {
    return this.http.put<ILaboratoryRequest>(`${this.resourceUrl}/${this.getLaboratoryIdentifier(laboratoryRequest)}`, laboratoryRequest, {
      observe: 'response',
    });
  }

  partialUpdate(laboratory: PartialUpdateDeveloper): Observable<EntityResponseType> {
    return this.http.patch<ILaboratoryRequest>(`${this.resourceUrl}/${this.getLaboratoryIdentifier(laboratory)}`, laboratory, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ILaboratoryRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILaboratoryRequest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLaboratoryIdentifier(laboratory: Pick<ILaboratoryRequest, 'laboratoryRequestId'>): string {
    return laboratory.laboratoryRequestId;
  }

  compareLaboratory(
    o1: Pick<ILaboratoryRequest, 'laboratoryRequestId'> | null,
    o2: Pick<ILaboratoryRequest, 'laboratoryRequestId'> | null
  ): boolean {
    return o1 && o2 ? this.getLaboratoryIdentifier(o1) === this.getLaboratoryIdentifier(o2) : o1 === o2;
  }

  searchLaboratoryRequest(req?: Search): Observable<HttpResponse<ILaboratoryRequest[]>> {
    const options = createRequestOption(req);
    return this.http.get<ILaboratoryRequest[]>(`${this.resourceUrl}/search`, { params: options, observe: 'response' });
  }

  addLaboratoryToCollectionIfMissing<Type extends Pick<ILaboratoryRequest, 'laboratoryRequestId'>>(
    laboratoryCollection: Type[],
    ...laboratoriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const laboratories: Type[] = laboratoriesToCheck.filter(isPresent);
    if (laboratories.length > 0) {
      const laboratoryCollectionIdentifiers = laboratoryCollection.map(laboratoryItem => this.getLaboratoryIdentifier(laboratoryItem)!);
      const laboratoriesToAdd = laboratories.filter(laboratoryItem => {
        const laboratoryIdentifier = this.getLaboratoryIdentifier(laboratoryItem);
        if (laboratoryCollectionIdentifiers.includes(laboratoryIdentifier)) {
          return false;
        }
        laboratoryCollectionIdentifiers.push(laboratoryIdentifier);
        return true;
      });
      return [...laboratoriesToAdd, ...laboratoryCollection];
    }
    return laboratoryCollection;
  }
}
