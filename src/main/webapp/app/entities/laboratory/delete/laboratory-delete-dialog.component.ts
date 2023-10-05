import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILaboratory } from '../laboratory.model';
import { LaboratoryService } from '../service/laboratory.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './laboratory-delete-dialog.component.html',
})
export class LaboratoryDeleteDialogComponent {
  laboratory?: ILaboratory;

  constructor(protected laboratoryService: LaboratoryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.laboratoryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
