import { NgModule } from '@angular/core';
import SharedModule from 'app/shared/shared.module';

import { RouterModule } from '@angular/router';
import { PatientListingComponent } from './patient-listing/patient-listing.component';

import routes from './patient.routing';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(routes)],
  declarations: [PatientListingComponent],
})
export class PatientModule {}
