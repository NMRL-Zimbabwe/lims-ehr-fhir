import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap } from '@angular/router';
import { Observable, combineLatest } from 'rxjs';
import { finalize, switchMap, tap } from 'rxjs/operators';

import { MappingFormService, MappingFormGroup } from './mapping-form.service';
import { IEhrLimsMapping } from '../mapping.model';
import { EntityMappingDataResponseType, MappingService } from '../service/mapping.service';
import { PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { SORT, DEFAULT_SORT_DATA, ASC, DESC } from 'app/config/navigation.constants';
import { IMappingData } from '../models/mappingData';

@Component({
  selector: 'jhi-mapping-update',
  templateUrl: './mapping-update.component.html',
})
export class MappingUpdateComponent implements OnInit {
  isSaving = false;
  mapping: IEhrLimsMapping | null = null;
  mappingsData?: IMappingData;
  isLoading = false;

  predicate = 'id';
  ascending = true;
  queryString = '';
  showRegistrationButtons = true;

  itemsPerPage = 1000;
  totalItems = 0;
  page = 1;

  editForm: MappingFormGroup = this.mappingFormService.createMappingFormGroup();

  constructor(
    protected mappingService: MappingService,
    protected mappingFormService: MappingFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mapping }) => {
      this.mapping = mapping;
      console.log(mapping); // eslint-disable-line no-console
      if (mapping) {
        this.updateForm(mapping);
      }
    });
    this.load();
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mapping = this.mappingFormService.getDeveloper(this.editForm);
    if (mapping.id !== null) {
      this.subscribeToSaveResponse(this.mappingService.update(mapping));
    } else {
      this.subscribeToSaveResponse(this.mappingService.create(mapping));
    }
  }

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityMappingDataResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityMappingDataResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.page, this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }
  protected onResponseSuccess(response: EntityMappingDataResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body!);
    this.mappingsData = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: IMappingData): IMappingData {
    return data;
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected queryBackend(page?: number, predicate?: string, ascending?: boolean): Observable<EntityMappingDataResponseType> {
    this.isLoading = true;
    const pageToLoad: number = page ?? 1;
    const queryObject = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.mappingService.getMapperData(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEhrLimsMapping>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(mapping: IEhrLimsMapping): void {
    this.mapping = mapping;
    this.mappingFormService.resetForm(this.editForm, mapping);
  }
}
