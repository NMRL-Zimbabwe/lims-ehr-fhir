import { ILaboratory } from 'app/entities/laboratory/laboratory.model';
import { IEhrSampleType } from './ehrSampleType';
import { IEhrTest } from './ehrTest';
import { ISampleType } from './sampleType';
import { ITest } from './test';

export interface IMappingData {
  ehrSampleTypes?: IEhrSampleType[] | null;
  ehrTests?: IEhrTest[] | null;
  limsSampleTypes?: ISampleType[] | null;
  limsTests?: ITest[] | null;
  laboratories?: ILaboratory[] | null;
}

export type NewIMappingData = Omit<IMappingData, 'id'> & { id: null };
