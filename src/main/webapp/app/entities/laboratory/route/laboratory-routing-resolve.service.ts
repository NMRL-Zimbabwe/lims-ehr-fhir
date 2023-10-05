import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILaboratory } from '../laboratory.model';
import { LaboratoryService } from '../service/laboratory.service';

@Injectable({ providedIn: 'root' })
export class LaboratoryRoutingResolveService implements Resolve<ILaboratory | null> {
  constructor(protected service: LaboratoryService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILaboratory | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((laboratory: HttpResponse<ILaboratory>) => {
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
