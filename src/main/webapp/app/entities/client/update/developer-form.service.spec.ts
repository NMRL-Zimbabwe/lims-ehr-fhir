import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../developer.test-samples';

import { DeveloperFormService } from './developer-form.service';

describe('Developer Form Service', () => {
  let service: DeveloperFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeveloperFormService);
  });

  describe('Service methods', () => {
    describe('createDeveloperFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDeveloperFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            streetAddress: expect.any(Object),
            postalCode: expect.any(Object),
            city: expect.any(Object),
            stateProvince: expect.any(Object),
          })
        );
      });

      it('passing IDeveloper should create a new form with FormGroup', () => {
        const formGroup = service.createDeveloperFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            streetAddress: expect.any(Object),
            postalCode: expect.any(Object),
            city: expect.any(Object),
            stateProvince: expect.any(Object),
          })
        );
      });
    });

    describe('getDeveloper', () => {
      it('should return NewDeveloper for default Developer initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDeveloperFormGroup(sampleWithNewData);

        const developer = service.getDeveloper(formGroup) as any;

        expect(developer).toMatchObject(sampleWithNewData);
      });

      it('should return NewDeveloper for empty Developer initial value', () => {
        const formGroup = service.createDeveloperFormGroup();

        const developer = service.getDeveloper(formGroup) as any;

        expect(developer).toMatchObject({});
      });

      it('should return IDeveloper', () => {
        const formGroup = service.createDeveloperFormGroup(sampleWithRequiredData);

        const developer = service.getDeveloper(formGroup) as any;

        expect(developer).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDeveloper should not enable id FormControl', () => {
        const formGroup = service.createDeveloperFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDeveloper should disable id FormControl', () => {
        const formGroup = service.createDeveloperFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
