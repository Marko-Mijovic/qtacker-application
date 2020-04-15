import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QtackerApplicationTestModule } from '../../../test.module';
import { CompanyExternDetailComponent } from 'app/entities/company-extern/company-extern-detail.component';
import { CompanyExtern } from 'app/shared/model/company-extern.model';

describe('Component Tests', () => {
  describe('CompanyExtern Management Detail Component', () => {
    let comp: CompanyExternDetailComponent;
    let fixture: ComponentFixture<CompanyExternDetailComponent>;
    const route = ({ data: of({ companyExtern: new CompanyExtern(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QtackerApplicationTestModule],
        declarations: [CompanyExternDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CompanyExternDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompanyExternDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load companyExtern on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.companyExtern).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
