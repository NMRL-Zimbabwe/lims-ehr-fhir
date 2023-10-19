export interface IPatient {
  patientId: string;
  firstname?: string | null;
  lastname?: string | null;
  gender?: string | null;
  primary_referrer?: string | null;
  primary_referrer_id?: string | null;
  dob?: string | null;
  art?: string | null;
}

export type NewPatient = Omit<IPatient, 'patientId'> & { patientId: null };
