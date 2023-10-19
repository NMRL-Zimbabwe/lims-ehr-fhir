import { IPatient } from '../patient/patient.model';

export interface ILaboratoryRequest {
  laboratoryRequestId: string;
  labId?: string | null;
  labName?: string | null;
  clientSampleId?: string | null;
  patient: IPatient;
  sampleId?: string | null;
  testName?: string | null;
  sampleTypeId?: string | null;
  sampleTypeName?: string | null;
  labReferenceSampleId?: string | null;
  result?: string | null;
  dateCollected?: Date | null;
  dateTested?: Date | null;
  reviewState?: string | null;
  status?: string | null;
  resultStatus?: string | null;
  dateResultReceivedFromLims?: Date | null;
}

export type NewLaboratoryRequest = Omit<ILaboratoryRequest, 'laboratoryRequestId'> & { laboratoryRequestId: null };
