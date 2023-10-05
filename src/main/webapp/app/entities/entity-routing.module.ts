import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

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
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
