import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QtackerApplicationTestModule } from '../../../test.module';
import { LogUserUpdateComponent } from 'app/entities/log-user/log-user-update.component';
import { LogUserService } from 'app/entities/log-user/log-user.service';
import { LogUser } from 'app/shared/model/log-user.model';

describe('Component Tests', () => {
  describe('LogUser Management Update Component', () => {
    let comp: LogUserUpdateComponent;
    let fixture: ComponentFixture<LogUserUpdateComponent>;
    let service: LogUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QtackerApplicationTestModule],
        declarations: [LogUserUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LogUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LogUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LogUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LogUser(123);
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
        const entity = new LogUser();
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
