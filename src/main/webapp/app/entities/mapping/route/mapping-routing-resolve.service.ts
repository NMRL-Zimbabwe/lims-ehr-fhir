import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEhrLimsMapping } from '../mapping.model';
import { MappingService } from '../service/mapping.service';

@Injectable({ providedIn: 'root' })
export class MappingRoutingResolveService implements Resolve<IEhrLimsMapping | null> {
  constructor(protected service: MappingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEhrLimsMapping | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((client: HttpResponse<IEhrLimsMapping>) => {
          if (client.body) {
            return of(client.body);
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
