import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILaboratory, NewLaboratory } from '../laboratory.model';
import { Search } from 'app/core/request/request.model';

export type PartialUpdateDeveloper = Partial<ILaboratory> & Pick<ILaboratory, 'id'>;

export type EntityResponseType = HttpResponse<ILaboratory>;
export type EntityArrayResponseType = HttpResponse<ILaboratory[]>;

@Injectable({ providedIn: 'root' })
export class LaboratoryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/laboratories');

  protected resourceUpdateUrl = this.applicationConfigService.getEndpointFor('api/update-laboratories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(laboratory: NewLaboratory): Observable<EntityResponseType> {
    return this.http.post<ILaboratory>(this.resourceUrl, laboratory, { observe: 'response' });
  }

  update(laboratory: ILaboratory): Observable<EntityResponseType> {
    return this.http.put<ILaboratory>(`${this.resourceUpdateUrl}`, laboratory, {
      observe: 'response',
    });
  }

  partialUpdate(laboratory: PartialUpdateDeveloper): Observable<EntityResponseType> {
    return this.http.patch<ILaboratory>(`${this.resourceUrl}/${this.getLaboratoryIdentifier(laboratory)}`, laboratory, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ILaboratory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILaboratory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLaboratoryIdentifier(laboratory: Pick<ILaboratory, 'id'>): string {
    return laboratory.id;
  }

  compareLaboratory(o1: Pick<ILaboratory, 'id'> | null, o2: Pick<ILaboratory, 'id'> | null): boolean {
    return o1 && o2 ? this.getLaboratoryIdentifier(o1) === this.getLaboratoryIdentifier(o2) : o1 === o2;
  }

  search(req?: Search): Observable<HttpResponse<ILaboratory[]>> {
    const options = createRequestOption(req);
    return this.http.get<ILaboratory[]>(`${this.resourceUrl}/search`, { params: options, observe: 'response' });
  }

  addLaboratoryToCollectionIfMissing<Type extends Pick<ILaboratory, 'id'>>(
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
