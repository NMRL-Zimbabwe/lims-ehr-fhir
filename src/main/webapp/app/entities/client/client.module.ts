import { NgModule } from '@angular/core';
import SharedModule from 'app/shared/shared.module';
import { ClientComponent } from './list/client.component';
import { ClientDetailComponent } from './detail/client-detail.component';
import { ClientUpdateComponent } from './update/client-update.component';
import { ClientDeleteDialogComponent } from './delete/client-delete-dialog.component';
import { ClientRoutingModule } from './route/client-routing.module';
import { ItemCountComponent } from 'app/shared/pagination';
import { SortDirective, SortByDirective } from 'app/shared/sort';

@NgModule({
  imports: [SharedModule, ClientRoutingModule, SortDirective, SortByDirective, ItemCountComponent],
  declarations: [ClientComponent, ClientDetailComponent, ClientUpdateComponent, ClientDeleteDialogComponent],
})
export class ClientModule {}
