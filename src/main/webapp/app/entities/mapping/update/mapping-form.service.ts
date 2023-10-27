import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEhrLimsMapping, NewEhrLimsMapping } from '../mapping.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEhrLimsMapping for edit and NewDeveloperFormGroupInput for create.
 */
type MappingFormGroupInput = IEhrLimsMapping | PartialWithRequiredKeyOf<NewEhrLimsMapping>;

type MappingFormDefaults = Pick<NewEhrLimsMapping, 'id'>;

type MappingFormGroupContent = {
  id: FormControl<IEhrLimsMapping['id'] | NewEhrLimsMapping['id']>;
  ehrSampleTypeId: FormControl<IEhrLimsMapping['ehrSampleTypeId']>;
  ehrTestId: FormControl<IEhrLimsMapping['ehrTestId']>;
  limsSampleTypeId: FormControl<IEhrLimsMapping['limsSampleTypeId']>;
  limsTestId: FormControl<IEhrLimsMapping['limsTestId']>;
  labratoryId: FormControl<IEhrLimsMapping['labratoryId']>;
};

export type MappingFormGroup = FormGroup<MappingFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MappingFormService {
  createMappingFormGroup(client: MappingFormGroupInput = { id: null }): MappingFormGroup {
    const developerRawValue = {
      ...this.getFormDefaults(),
      ...client,
    };
    return new FormGroup<MappingFormGroupContent>({
      id: new FormControl(
        { value: developerRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      ehrSampleTypeId: new FormControl(developerRawValue.ehrSampleTypeId),
      ehrTestId: new FormControl(developerRawValue.ehrTestId),
      limsSampleTypeId: new FormControl(developerRawValue.limsSampleTypeId),
      limsTestId: new FormControl(developerRawValue.limsTestId),
      labratoryId: new FormControl(developerRawValue.labratoryId),
    });
  }

  getDeveloper(form: MappingFormGroup): IEhrLimsMapping | NewEhrLimsMapping {
    return form.getRawValue() as IEhrLimsMapping | NewEhrLimsMapping;
  }

  resetForm(form: MappingFormGroup, client: MappingFormGroupInput): void {
    const developerRawValue = { ...this.getFormDefaults(), ...client };
    form.reset(
      {
        ...developerRawValue,
        id: { value: developerRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MappingFormDefaults {
    return {
      id: null,
    };
  }
}
