import { TestBed, async } from '@angular/core/testing';
import { PublicComponent } from './public.component';

describe('PublicComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        PublicComponent
      ],
    }).compileComponents();
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(PublicComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'appengine-web'`, () => {
    const fixture = TestBed.createComponent(PublicComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('appengine-web');
  });

  it('should render title in a h1 tag', () => {
    const fixture = TestBed.createComponent(PublicComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('Welcome to appengine-web!');
  });
});
