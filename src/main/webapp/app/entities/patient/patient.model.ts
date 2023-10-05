export interface IPatient {
  patientId: string;
  firstname: string;
  lastname: string;
  gender: string;
  primaryReferrer: string;
  primaryReferrerId: string;
  art: string;
  dob: string;
}

export class Patient implements IPatient {
  constructor(
    public patientId: string,
    public firstname: string,
    public lastname: string,
    public gender: string,
    public primaryReferrer: string,
    public primaryReferrerId: string,
    public art: string,
    public dob: string
  ) {}
}
