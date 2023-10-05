import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IClient, NewClient } from '../client.model';
import { Search } from 'app/core/request/request.model';

export type PartialUpdateDeveloper = Partial<IClient> & Pick<IClient, 'id'>;

export type EntityResponseType = HttpResponse<IClient>;
export type EntityArrayResponseType = HttpResponse<IClient[]>;

@Injectable({ providedIn: 'root' })
export class ClientService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/clients');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(client: NewClient): Observable<EntityResponseType> {
    return this.http.post<IClient>(this.resourceUrl, client, { observe: 'response' });
  }

  update(client: IClient): Observable<EntityResponseType> {
    return this.http.put<IClient>(`${this.resourceUrl}/${this.getClientIdentifier(client)}`, client, { observe: 'response' });
  }

  partialUpdate(client: PartialUpdateDeveloper): Observable<EntityResponseType> {
    return this.http.patch<IClient>(`${this.resourceUrl}/${this.getClientIdentifier(client)}`, client, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IClient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClient[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getClientIdentifier(client: Pick<IClient, 'id'>): string {
    return client.id;
  }

  compareDeveloper(o1: Pick<IClient, 'id'> | null, o2: Pick<IClient, 'id'> | null): boolean {
    return o1 && o2 ? this.getClientIdentifier(o1) === this.getClientIdentifier(o2) : o1 === o2;
  }

  search(req?: Search): Observable<HttpResponse<IClient[]>> {
    const options = createRequestOption(req);
    return this.http.get<IClient[]>(`${this.resourceUrl}/search`, { params: options, observe: 'response' });
  }

  addDeveloperToCollectionIfMissing<Type extends Pick<IClient, 'id'>>(
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
