import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LaboratoryComponent } from '../list/laboratory.component';
import { LaboratoryDetailComponent } from '../detail/laboratory-detail.component';
import { LaboratoryUpdateComponent } from '../update/laboratory-update.component';
import { LaboratoryRoutingResolveService } from './laboratory-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const laboratoryRoute: Routes = [
  {
    path: '',
    component: LaboratoryComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LaboratoryDetailComponent,
    resolve: {
      laboratory: LaboratoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LaboratoryUpdateComponent,
    resolve: {
      laboratory: LaboratoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LaboratoryUpdateComponent,
    resolve: {
      laboratory: LaboratoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(laboratoryRoute)],
  exports: [RouterModule],
})
export class LaboratoryRoutingModule {}
