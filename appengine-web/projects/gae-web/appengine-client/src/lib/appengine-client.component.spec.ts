import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppengineClientComponent } from './appengine-client.component';

describe('AppengineClientComponent', () => {
  let component: AppengineClientComponent;
  let fixture: ComponentFixture<AppengineClientComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppengineClientComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppengineClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
