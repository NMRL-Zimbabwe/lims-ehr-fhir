import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPatient, NewPatient } from '../patient.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { patientId: unknown }> = Partial<Omit<T, 'patientId'>> & { patientId: T['patientId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPatient for edit and NewDeveloperFormGroupInput for create.
 */
type PatientFormGroupInput = IPatient | PartialWithRequiredKeyOf<NewPatient>;

type PatientFormDefaults = Pick<NewPatient, 'patientId'>;

type PatientFormGroupContent = {
  patientId: FormControl<IPatient['patientId'] | NewPatient['patientId']>;
  firstname: FormControl<IPatient['firstname']>;
  lastname: FormControl<IPatient['lastname']>;
  gender: FormControl<IPatient['gender']>;
  primary_referrer: FormControl<IPatient['primary_referrer']>;
  primary_referrer_id: FormControl<IPatient['primary_referrer_id']>;
  art: FormControl<IPatient['art']>;
  dob: FormControl<IPatient['dob']>;
};

export type PatientFormGroup = FormGroup<PatientFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PatientFormService {
  createPatientFormGroup(patient: PatientFormGroupInput = { patientId: null }): PatientFormGroup {
    const patientRawValue = {
      ...this.getFormDefaults(),
      ...patient,
    };
    return new FormGroup<PatientFormGroupContent>({
      patientId: new FormControl(
        { value: patientRawValue.patientId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      firstname: new FormControl(patientRawValue.firstname),
      lastname: new FormControl(patientRawValue.lastname),
      gender: new FormControl(patientRawValue.gender),
      primary_referrer: new FormControl(patientRawValue.primary_referrer),
      primary_referrer_id: new FormControl(patientRawValue.primary_referrer_id),
      art: new FormControl(patientRawValue.art),
      dob: new FormControl(patientRawValue.dob),
    });
  }

  getPatient(form: PatientFormGroup): IPatient | NewPatient {
    return form.getRawValue() as IPatient | NewPatient;
  }

  resetForm(form: PatientFormGroup, patient: PatientFormGroupInput): void {
    const patientRawValue = { ...this.getFormDefaults(), ...patient };
    form.reset(
      {
        ...patientRawValue,
        id: { value: patientRawValue.patientId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PatientFormDefaults {
    return {
      patientId: null,
    };
  }
}
