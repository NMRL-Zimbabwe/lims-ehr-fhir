export interface ISampleType {
  id: string;
  name?: string | null;
  sampleTypeId?: string | null;
}

export type NewISampleType = Omit<ISampleType, 'id'> & { id: null };
