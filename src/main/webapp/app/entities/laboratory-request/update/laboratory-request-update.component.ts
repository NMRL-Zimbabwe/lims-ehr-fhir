import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { LaboratoryRequestFormService, LaboratoryRequestFormGroup } from './laboratory-request-form.service';
import { ILaboratoryRequest } from '../laboratory-request.model';
import { LaboratoryRequestService } from '../service/laboratory-request.service';

@Component({
  selector: 'jhi-laboratory-request-update',
  templateUrl: './laboratory-request-update.component.html',
})
export class LaboratoryRequestUpdateComponent implements OnInit {
  isSaving = false;
  laboratory: ILaboratoryRequest | null = null;

  editForm: LaboratoryRequestFormGroup = this.laboratoryFormService.createLaboratoryFormGroup();

  constructor(
    protected LaboratoryService: LaboratoryRequestService,
    protected laboratoryFormService: LaboratoryRequestFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ laboratory }) => {
      this.laboratory = laboratory;
      if (laboratory) {
        this.updateForm(laboratory);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const laboratory = this.laboratoryFormService.getLaboratory(this.editForm);
    if (laboratory.laboratoryRequestId !== null) {
      this.subscribeToSaveResponse(this.LaboratoryService.update(laboratory));
    } else {
      this.subscribeToSaveResponse(this.LaboratoryService.create(laboratory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILaboratoryRequest>>): void {
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

  protected updateForm(laboratory: ILaboratoryRequest): void {
    this.laboratory = laboratory;
    this.laboratoryFormService.resetForm(this.editForm, laboratory);
  }
}
