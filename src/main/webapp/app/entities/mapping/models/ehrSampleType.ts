export interface IEhrSampleType {
  id: string;
  name?: string | null;
  ehrCode?: string | null;
  loincCode?: string | null;
}

export type NewIEhrSampleType = Omit<IEhrSampleType, 'id'> & { id: null };
