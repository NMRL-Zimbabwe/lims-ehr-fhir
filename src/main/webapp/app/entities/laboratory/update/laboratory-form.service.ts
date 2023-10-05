import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILaboratory, NewLaboratory } from '../laboratory.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILaboratory for edit and NewDeveloperFormGroupInput for create.
 */
type LaboratoryFormGroupInput = ILaboratory | PartialWithRequiredKeyOf<NewLaboratory>;

type LaboratoryFormDefaults = Pick<NewLaboratory, 'id'>;

type LaboratoryFormGroupContent = {
  id: FormControl<ILaboratory['id'] | NewLaboratory['id']>;
  name: FormControl<ILaboratory['name']>;
  code: FormControl<ILaboratory['code']>;
  type: FormControl<ILaboratory['type']>;
};

export type LaboratoryFormGroup = FormGroup<LaboratoryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LaboratoryFormService {
  createLaboratoryFormGroup(laboratory: LaboratoryFormGroupInput = { id: null }): LaboratoryFormGroup {
    const laboratoryRawValue = {
      ...this.getFormDefaults(),
      ...laboratory,
    };
    return new FormGroup<LaboratoryFormGroupContent>({
      id: new FormControl(
        { value: laboratoryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(laboratoryRawValue.name),
      code: new FormControl(laboratoryRawValue.code),
      type: new FormControl(laboratoryRawValue.type),
    });
  }

  getLaboratory(form: LaboratoryFormGroup): ILaboratory | NewLaboratory {
    return form.getRawValue() as ILaboratory | NewLaboratory;
  }

  resetForm(form: LaboratoryFormGroup, laboratory: LaboratoryFormGroupInput): void {
    const laboratoryRawValue = { ...this.getFormDefaults(), ...laboratory };
    form.reset(
      {
        ...laboratoryRawValue,
        id: { value: laboratoryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LaboratoryFormDefaults {
    return {
      id: null,
    };
  }
}
