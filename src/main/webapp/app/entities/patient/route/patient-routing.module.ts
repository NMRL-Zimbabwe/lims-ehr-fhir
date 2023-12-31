import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PatientComponent } from '../list/patient.component';
import { PatientDetailComponent } from '../detail/patient-detail.component';
import { PatientUpdateComponent } from '../update/patient-update.component';
import { PatientRoutingResolveService } from './patient-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const patientRoute: Routes = [
  {
    path: '',
    component: PatientComponent,
    data: {
      defaultSort: 'patientId,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':patientId/view',
    component: PatientDetailComponent,
    resolve: {
      patient: PatientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PatientUpdateComponent,
    resolve: {
      laboratory: PatientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':patientId/edit',
    component: PatientUpdateComponent,
    resolve: {
      patient: PatientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(patientRoute)],
  exports: [RouterModule],
})
export class PatientRoutingModule {}
