import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { GaeGatewayViewsModule } from './view.module';
import { GaeGatewayBoxViewComponent } from './box.component';

describe('GaeGatewayBoxViewComponent', () => {
  let component: GaeGatewayBoxViewComponent;
  let fixture: ComponentFixture<GaeGatewayBoxViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeGatewayViewsModule.forRoot({})
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GaeGatewayBoxViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
