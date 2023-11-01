import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEhrLimsMapping } from '../mapping.model';
import { MappingService } from '../service/mapping.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './mapping-delete-dialog.component.html',
})
export class MappingDeleteDialogComponent {
  mapping?: IEhrLimsMapping;

  constructor(protected mappingService: MappingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.mappingService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
