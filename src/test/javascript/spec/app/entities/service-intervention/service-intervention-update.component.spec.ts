import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QtackerApplicationTestModule } from '../../../test.module';
import { ServiceInterventionUpdateComponent } from 'app/entities/service-intervention/service-intervention-update.component';
import { ServiceInterventionService } from 'app/entities/service-intervention/service-intervention.service';
import { ServiceIntervention } from 'app/shared/model/service-intervention.model';

describe('Component Tests', () => {
  describe('ServiceIntervention Management Update Component', () => {
    let comp: ServiceInterventionUpdateComponent;
    let fixture: ComponentFixture<ServiceInterventionUpdateComponent>;
    let service: ServiceInterventionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QtackerApplicationTestModule],
        declarations: [ServiceInterventionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceInterventionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceInterventionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceInterventionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceIntervention(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceIntervention();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
