import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { PatientListingComponent } from './patient-listing/patient-listing.component';
import { PatientService } from './patient.service';
import { IPatient, Patient } from './patient.model';
import { Authority } from 'app/config/authority.constants';

const routes: Routes = [
  {
    path: '',
    component: PatientListingComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'patientId,asc',
      pageTitle: 'Patient Listing',
    },
    // canActivate: [UserRouteAccessService],
    // resolve: {data: () => inject(PatientService).getByPatientId()},
  },
];

export default routes;
