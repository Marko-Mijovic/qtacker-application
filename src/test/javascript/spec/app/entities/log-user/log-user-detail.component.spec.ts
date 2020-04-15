import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QtackerApplicationTestModule } from '../../../test.module';
import { LogUserDetailComponent } from 'app/entities/log-user/log-user-detail.component';
import { LogUser } from 'app/shared/model/log-user.model';

describe('Component Tests', () => {
  describe('LogUser Management Detail Component', () => {
    let comp: LogUserDetailComponent;
    let fixture: ComponentFixture<LogUserDetailComponent>;
    const route = ({ data: of({ logUser: new LogUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QtackerApplicationTestModule],
        declarations: [LogUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LogUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LogUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load logUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.logUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
