import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Authority } from 'app/config/authority.constants';

@NgModule({
  imports: [
    RouterModule.forChild([
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
