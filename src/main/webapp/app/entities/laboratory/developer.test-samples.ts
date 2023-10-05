import { IDeveloper, NewDeveloper } from './developer.model';

export const sampleWithRequiredData: IDeveloper = {
  id: 61823,
};

export const sampleWithPartialData: IDeveloper = {
  id: 85461,
  streetAddress: 'Pines Michigan',
};

export const sampleWithFullData: IDeveloper = {
  id: 63277,
  streetAddress: 'SDD',
  postalCode: 'Grocery',
  city: 'Dahliaton',
  stateProvince: 'logistical systematic utilize',
};

export const sampleWithNewData: NewDeveloper = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
