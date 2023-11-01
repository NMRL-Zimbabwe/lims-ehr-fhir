import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MappingComponent } from '../list/mapping.component';
import { MappingUpdateComponent } from '../update/mapping-update.component';
import { MappingRoutingResolveService } from './mapping-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const mappingRoute: Routes = [
  {
    path: '',
    component: MappingComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MappingUpdateComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    resolve: {
      mapping: MappingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MappingUpdateComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    resolve: {
      mapping: MappingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mappingRoute)],
  exports: [RouterModule],
})
export class MappingRoutingModule {}
