import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILaboratory } from '../laboratory.model';

@Component({
  selector: 'jhi-laboratory-detail',
  templateUrl: './laboratory-detail.component.html',
})
export class LaboratoryDetailComponent implements OnInit {
  laboratory: ILaboratory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ laboratory }) => {
      this.laboratory = laboratory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
