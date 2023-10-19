import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILaboratoryRequest } from '../laboratory-request.model';
import { LaboratoryRequestService } from '../service/laboratory-request.service';

@Injectable({ providedIn: 'root' })
export class LaboratoryRequestRoutingResolveService implements Resolve<ILaboratoryRequest | null> {
  constructor(protected service: LaboratoryRequestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILaboratoryRequest | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((laboratory: HttpResponse<ILaboratoryRequest>) => {
          if (laboratory.body) {
            return of(laboratory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
