import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILaboratoryRequest } from '../laboratory-request.model';

@Component({
  selector: 'jhi-laboratory-request-detail',
  templateUrl: './laboratory-request-detail.component.html',
})
export class LaboratoryRequestDetailComponent implements OnInit {
  laboratoryRequest: ILaboratoryRequest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ laboratoryRequest }) => {
      this.laboratoryRequest = laboratoryRequest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
