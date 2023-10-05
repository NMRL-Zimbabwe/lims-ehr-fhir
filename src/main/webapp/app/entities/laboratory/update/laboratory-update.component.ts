import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { LaboratoryFormService, LaboratoryFormGroup } from './laboratory-form.service';
import { ILaboratory } from '../laboratory.model';
import { LaboratoryService } from '../service/laboratory.service';

@Component({
  selector: 'jhi-laboratory-update',
  templateUrl: './laboratory-update.component.html',
})
export class LaboratoryUpdateComponent implements OnInit {
  isSaving = false;
  laboratory: ILaboratory | null = null;

  editForm: LaboratoryFormGroup = this.laboratoryFormService.createLaboratoryFormGroup();

  constructor(
    protected LaboratoryService: LaboratoryService,
    protected laboratoryFormService: LaboratoryFormService,
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
    if (laboratory.id !== null) {
      this.subscribeToSaveResponse(this.LaboratoryService.update(laboratory));
    } else {
      this.subscribeToSaveResponse(this.LaboratoryService.create(laboratory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILaboratory>>): void {
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

  protected updateForm(laboratory: ILaboratory): void {
    this.laboratory = laboratory;
    this.laboratoryFormService.resetForm(this.editForm, laboratory);
  }
}
