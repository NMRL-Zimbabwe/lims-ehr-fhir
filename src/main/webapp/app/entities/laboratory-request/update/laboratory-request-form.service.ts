import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILaboratoryRequest, NewLaboratoryRequest } from '../laboratory-request.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { laboratoryRequestId: unknown }> = Partial<Omit<T, 'laboratoryRequestId'>> & {
  laboratoryRequestId: T['laboratoryRequestId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILaboratory for edit and NewDeveloperFormGroupInput for create.
 */
type LaboratoryRequestFormGroupInput = ILaboratoryRequest | PartialWithRequiredKeyOf<NewLaboratoryRequest>;

type LaboratoryFormDefaults = Pick<NewLaboratoryRequest, 'laboratoryRequestId'>;

type LaboratoryRequestFormGroupContent = {
  laboratoryRequestId: FormControl<ILaboratoryRequest['laboratoryRequestId'] | NewLaboratoryRequest['laboratoryRequestId']>;
  //firstname: FormControl<ILaboratoryRequest['patient']>;
  //surname: FormControl<ILaboratoryRequest['patient']>;
  clientSampleId: FormControl<ILaboratoryRequest['clientSampleId']>;
  sampleId: FormControl<ILaboratoryRequest['sampleId']>;
  dateCollected: FormControl<ILaboratoryRequest['dateCollected']>;
  status: FormControl<ILaboratoryRequest['status']>;
  dateResultReceivedFromLims: FormControl<ILaboratoryRequest['dateResultReceivedFromLims']>;
};

export type LaboratoryRequestFormGroup = FormGroup<LaboratoryRequestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LaboratoryRequestFormService {
  createLaboratoryFormGroup(laboratory: LaboratoryRequestFormGroupInput = { laboratoryRequestId: null }): LaboratoryRequestFormGroup {
    const laboratoryRawValue = {
      ...this.getFormDefaults(),
      ...laboratory,
    };
    return new FormGroup<LaboratoryRequestFormGroupContent>({
      laboratoryRequestId: new FormControl(
        { value: laboratoryRawValue.laboratoryRequestId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      //firstname: new FormControl(laboratoryRawValue.patient?.firstname),
      //surname: new FormControl(laboratoryRawValue.patient?.lastname),
      clientSampleId: new FormControl(laboratoryRawValue.clientSampleId),
      sampleId: new FormControl(laboratoryRawValue.sampleId),
      dateCollected: new FormControl(laboratoryRawValue.dateCollected),
      status: new FormControl(laboratoryRawValue.status),
      dateResultReceivedFromLims: new FormControl(laboratoryRawValue.dateResultReceivedFromLims),
    });
  }

  getLaboratory(form: LaboratoryRequestFormGroup): ILaboratoryRequest | NewLaboratoryRequest {
    return form.getRawValue() as ILaboratoryRequest | NewLaboratoryRequest;
  }

  resetForm(form: LaboratoryRequestFormGroup, laboratory: LaboratoryRequestFormGroupInput): void {
    const laboratoryRawValue = { ...this.getFormDefaults(), ...laboratory };
    form.reset(
      {
        ...laboratoryRawValue,
        laboratoryRequestId: { value: laboratoryRawValue.laboratoryRequestId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LaboratoryFormDefaults {
    return {
      laboratoryRequestId: null,
    };
  }
}
