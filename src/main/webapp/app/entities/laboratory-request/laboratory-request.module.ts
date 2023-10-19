import { NgModule } from '@angular/core';
import SharedModule from 'app/shared/shared.module';
import { LaboratoryRequestComponent } from './list/laboratory-request.component';

import { LaboratoryRequestDetailComponent } from './detail/laboratory-request-detail.component';
import { LaboratoryRequestUpdateComponent } from './update/laboratory-request-update.component';
import { LaboratoryRequestDeleteDialogComponent } from './delete/laboratory-request-delete-dialog.component';
import { LaboratoryRequestRoutingModule } from './route/laboratory-request-routing.module';
import { ItemCountComponent } from 'app/shared/pagination';
import { SortDirective, SortByDirective } from 'app/shared/sort';

@NgModule({
  imports: [SharedModule, LaboratoryRequestRoutingModule, SortDirective, SortByDirective, ItemCountComponent],
  declarations: [
    LaboratoryRequestComponent,
    LaboratoryRequestDetailComponent,
    LaboratoryRequestUpdateComponent,
    LaboratoryRequestDeleteDialogComponent,
  ],
})
export class LaboratoryRequestModule {}
