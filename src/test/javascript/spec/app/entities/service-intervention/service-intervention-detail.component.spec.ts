import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QtackerApplicationTestModule } from '../../../test.module';
import { ServiceInterventionDetailComponent } from 'app/entities/service-intervention/service-intervention-detail.component';
import { ServiceIntervention } from 'app/shared/model/service-intervention.model';

describe('Component Tests', () => {
  describe('ServiceIntervention Management Detail Component', () => {
    let comp: ServiceInterventionDetailComponent;
    let fixture: ComponentFixture<ServiceInterventionDetailComponent>;
    const route = ({ data: of({ serviceIntervention: new ServiceIntervention(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QtackerApplicationTestModule],
        declarations: [ServiceInterventionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceInterventionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceInterventionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load serviceIntervention on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceIntervention).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
