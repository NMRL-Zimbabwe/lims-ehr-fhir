import { ILaboratory } from '../laboratory/laboratory.model';
import { IEhrSampleType } from './models/ehrSampleType';
import { IEhrTest } from './models/ehrTest';
import { ISampleType } from './models/sampleType';
import { ITest } from './models/test';

export interface IEhrLimsMapping {
  id: string;
  ehrSampleTypeId?: string | null;
  ehrSampleType?: IEhrSampleType | null;
  ehrTestId?: string | null;
  ehrTest?: IEhrTest | null;
  limsSampleTypeId?: string | null;
  limsSampleType?: ISampleType | null;
  limsTestId?: string | null;
  limsTest?: ITest | null;
  labratoryId?: string | null;
  labratory?: ILaboratory | null;
}

export type NewEhrLimsMapping = Omit<IEhrLimsMapping, 'id'> & { id: null };
