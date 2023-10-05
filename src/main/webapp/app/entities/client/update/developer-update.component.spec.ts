import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DeveloperFormService } from './developer-form.service';
import { DeveloperService } from '../service/client.service';
import { IDeveloper } from '../developer.model';

import { DeveloperUpdateComponent } from './client-update.component';

describe('Developer Management Update Component', () => {
  let comp: DeveloperUpdateComponent;
  let fixture: ComponentFixture<DeveloperUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let developerFormService: DeveloperFormService;
  let developerService: DeveloperService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DeveloperUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DeveloperUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DeveloperUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    developerFormService = TestBed.inject(DeveloperFormService);
    developerService = TestBed.inject(DeveloperService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const developer: IDeveloper = { id: 456 };

      activatedRoute.data = of({ developer });
      comp.ngOnInit();

      expect(comp.developer).toEqual(developer);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeveloper>>();
      const developer = { id: 123 };
      jest.spyOn(developerFormService, 'getDeveloper').mockReturnValue(developer);
      jest.spyOn(developerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ developer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: developer }));
      saveSubject.complete();

      // THEN
      expect(developerFormService.getDeveloper).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(developerService.update).toHaveBeenCalledWith(expect.objectContaining(developer));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeveloper>>();
      const developer = { id: 123 };
      jest.spyOn(developerFormService, 'getDeveloper').mockReturnValue({ id: null });
      jest.spyOn(developerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ developer: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: developer }));
      saveSubject.complete();

      // THEN
      expect(developerFormService.getDeveloper).toHaveBeenCalled();
      expect(developerService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeveloper>>();
      const developer = { id: 123 };
      jest.spyOn(developerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ developer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(developerService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
