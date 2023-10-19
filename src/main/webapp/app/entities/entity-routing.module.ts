import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Authority } from 'app/config/authority.constants';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'client',
        data: { pageTitle: 'limsEhrIntegrationApp.client.home.title' },
        loadChildren: () => import('./client/client.module').then(m => m.ClientModule),
      },
      {
        path: 'laboratory',
        data: { pageTitle: 'limsEhrIntegrationApp.laboratory.home.title' },
        loadChildren: () => import('./laboratory/laboratory.module').then(m => m.LaboratoryModule),
      },
      {
        path: 'laboratoryRequest',
        data: { pageTitle: 'limsEhrIntegrationApp.laboratoryRequest.home.title' },
        loadChildren: () => import('./laboratory-request/laboratory-request.module').then(m => m.LaboratoryRequestModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
      {
        path: 'patients',
        data: {
          authorities: [Authority.ADMIN],
        },
        loadChildren: () => import('./patient/patient.module').then(m => m.PatientModule),
      },
    ]),
  ],
})
export class EntityRoutingModule {}
