import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LaboratoryRequestComponent } from '../list/laboratory-request.component';
import { LaboratoryRequestDetailComponent } from '../detail/laboratory-request-detail.component';
import { LaboratoryRequestUpdateComponent } from '../update/laboratory-request-update.component';
import { LaboratoryRequestRoutingResolveService } from './laboratory-request-routing-resolve.service';
import { ASC, DESC } from 'app/config/navigation.constants';

const laboratoryRequestRoute: Routes = [
  {
    path: '',
    component: LaboratoryRequestComponent,
    data: {
      defaultSort: 'clientSampleId,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LaboratoryRequestDetailComponent,
    resolve: {
      laboratoryRequest: LaboratoryRequestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LaboratoryRequestUpdateComponent,
    resolve: {
      laboratory: LaboratoryRequestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LaboratoryRequestUpdateComponent,
    resolve: {
      laboratory: LaboratoryRequestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(laboratoryRequestRoute)],
  exports: [RouterModule],
})
export class LaboratoryRequestRoutingModule {}
