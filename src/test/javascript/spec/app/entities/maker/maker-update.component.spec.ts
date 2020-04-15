import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QtackerApplicationTestModule } from '../../../test.module';
import { MakerUpdateComponent } from 'app/entities/maker/maker-update.component';
import { MakerService } from 'app/entities/maker/maker.service';
import { Maker } from 'app/shared/model/maker.model';

describe('Component Tests', () => {
  describe('Maker Management Update Component', () => {
    let comp: MakerUpdateComponent;
    let fixture: ComponentFixture<MakerUpdateComponent>;
    let service: MakerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QtackerApplicationTestModule],
        declarations: [MakerUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MakerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MakerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MakerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Maker(123);
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
        const entity = new Maker();
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
