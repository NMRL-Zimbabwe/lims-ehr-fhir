import { NgModule } from '@angular/core';
import SharedModule from 'app/shared/shared.module';
import { PatientComponent } from './list/patient.component';

import { PatientDetailComponent } from './detail/patient-detail.component';
import { PatientUpdateComponent } from './update/patient-update.component';
import { PatientDeleteDialogComponent } from './delete/patient-delete-dialog.component';
import { PatientRoutingModule } from './route/patient-routing.module';
import { ItemCountComponent } from 'app/shared/pagination';
import { SortDirective, SortByDirective } from 'app/shared/sort';

@NgModule({
  imports: [SharedModule, PatientRoutingModule, SortDirective, SortByDirective, ItemCountComponent],
  declarations: [PatientComponent, PatientDetailComponent, PatientUpdateComponent, PatientDeleteDialogComponent],
})
export class PatientModule {}
