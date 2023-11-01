import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEhrLimsMapping, NewEhrLimsMapping } from '../mapping.model';
import { Search } from 'app/core/request/request.model';
import { IMappingData } from '../models/mappingData';

export type PartialUpdateDeveloper = Partial<IEhrLimsMapping> & Pick<IEhrLimsMapping, 'id'>;

export type EntityResponseType = HttpResponse<IEhrLimsMapping>;
export type EntityArrayResponseType = HttpResponse<IEhrLimsMapping[]>;
export type EntityMappingDataResponseType = HttpResponse<IMappingData>;

@Injectable({ providedIn: 'root' })
export class MappingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/lims-ehr-mappings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(client: NewEhrLimsMapping): Observable<EntityResponseType> {
    return this.http.post<IEhrLimsMapping>(this.resourceUrl, client, { observe: 'response' });
  }

  update(client: IEhrLimsMapping): Observable<EntityResponseType> {
    return this.http.put<IEhrLimsMapping>(`${this.resourceUrl}`, client, { observe: 'response' });
  }

  partialUpdate(client: PartialUpdateDeveloper): Observable<EntityResponseType> {
    return this.http.patch<IEhrLimsMapping>(`${this.resourceUrl}/${this.getClientIdentifier(client)}`, client, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IEhrLimsMapping>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEhrLimsMapping[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  getMapperData(req?: any): Observable<EntityMappingDataResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMappingData>(`${this.resourceUrl}/data`, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getClientIdentifier(client: Pick<IEhrLimsMapping, 'id'>): string {
    return client.id;
  }

  compareDeveloper(o1: Pick<IEhrLimsMapping, 'id'> | null, o2: Pick<IEhrLimsMapping, 'id'> | null): boolean {
    return o1 && o2 ? this.getClientIdentifier(o1) === this.getClientIdentifier(o2) : o1 === o2;
  }

  search(req?: Search): Observable<HttpResponse<IEhrLimsMapping[]>> {
    const options = createRequestOption(req);
    return this.http.get<IEhrLimsMapping[]>(`${this.resourceUrl}/search`, { params: options, observe: 'response' });
  }

  addDeveloperToCollectionIfMissing<Type extends Pick<IEhrLimsMapping, 'id'>>(
    developerCollection: Type[],
    ...developersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const developers: Type[] = developersToCheck.filter(isPresent);
    if (developers.length > 0) {
      const developerCollectionIdentifiers = developerCollection.map(developerItem => this.getClientIdentifier(developerItem)!);
      const developersToAdd = developers.filter(developerItem => {
        const developerIdentifier = this.getClientIdentifier(developerItem);
        if (developerCollectionIdentifiers.includes(developerIdentifier)) {
          return false;
        }
        developerCollectionIdentifiers.push(developerIdentifier);
        return true;
      });
      return [...developersToAdd, ...developerCollection];
    }
    return developerCollection;
  }
}
