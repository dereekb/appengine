import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppengineGatewayComponent } from './appengine-gateway.component';

describe('AppengineGatewayComponent', () => {
  let component: AppengineGatewayComponent;
  let fixture: ComponentFixture<AppengineGatewayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppengineGatewayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppengineGatewayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
