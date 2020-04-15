import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QtackerApplicationTestModule } from '../../../test.module';
import { MakerDetailComponent } from 'app/entities/maker/maker-detail.component';
import { Maker } from 'app/shared/model/maker.model';

describe('Component Tests', () => {
  describe('Maker Management Detail Component', () => {
    let comp: MakerDetailComponent;
    let fixture: ComponentFixture<MakerDetailComponent>;
    const route = ({ data: of({ maker: new Maker(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QtackerApplicationTestModule],
        declarations: [MakerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MakerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MakerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load maker on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.maker).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
