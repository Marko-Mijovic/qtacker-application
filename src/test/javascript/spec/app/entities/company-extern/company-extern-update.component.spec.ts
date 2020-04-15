import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QtackerApplicationTestModule } from '../../../test.module';
import { CompanyExternUpdateComponent } from 'app/entities/company-extern/company-extern-update.component';
import { CompanyExternService } from 'app/entities/company-extern/company-extern.service';
import { CompanyExtern } from 'app/shared/model/company-extern.model';

describe('Component Tests', () => {
  describe('CompanyExtern Management Update Component', () => {
    let comp: CompanyExternUpdateComponent;
    let fixture: ComponentFixture<CompanyExternUpdateComponent>;
    let service: CompanyExternService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QtackerApplicationTestModule],
        declarations: [CompanyExternUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CompanyExternUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompanyExternUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanyExternService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompanyExtern(123);
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
        const entity = new CompanyExtern();
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
