import { NgModule } from '@angular/core';
import SharedModule from 'app/shared/shared.module';
import { MappingComponent } from './list/mapping.component';
import { MappingUpdateComponent } from './update/mapping-update.component';
import { MappingDeleteDialogComponent } from './delete/mapping-delete-dialog.component';
import { MappingRoutingModule } from './route/mapping-routing.module';
import { ItemCountComponent } from 'app/shared/pagination';
import { SortDirective, SortByDirective } from 'app/shared/sort';

@NgModule({
  imports: [SharedModule, MappingRoutingModule, SortDirective, SortByDirective, ItemCountComponent],
  declarations: [MappingComponent, MappingUpdateComponent, MappingDeleteDialogComponent],
})
export class MappingModule {}
