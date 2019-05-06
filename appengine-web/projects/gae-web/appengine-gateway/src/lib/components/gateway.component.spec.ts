import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GaeGatewayComponentsModule } from './components.module';
import { Component } from '@angular/core';
import { SignInGateway, AbstractSignInGateway } from './gateway';
import { By } from '@angular/platform-browser';
import { LoginTokenPair } from '@gae-web/appengine-token';

describe('GaeGatewayComponentsModule', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeGatewayComponentsModule
      ],
      declarations: [SignInGatewayViewComponent]
    }).compileComponents();
  }));

  let component: SignInGatewayViewComponent;
  let fixture: ComponentFixture<SignInGatewayViewComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(SignInGatewayViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  describe('initially', () => {

    it('should show the sign in view.', () => {
      const testContent: HTMLElement = fixture.debugElement.query(By.css('#' + SIGN_IN_BUTTON)).nativeElement;
      expect(testContent).not.toBeNull();
    });

  });

  describe('while logging in', () => {

    beforeEach(async(() => {
      component.gateway.setWorking();
      fixture.detectChanges();
    }));

    it('should show the loading view.', () => {
      const loadingProgressQueryResult = fixture.debugElement.query(By.directive(GaeLoadingProgressComponent));
      expect(loadingProgressQueryResult).not.toBeNull();
    });

  });

  describe('on success', () => {

    beforeEach(async(() => {
      component.gateway.setToken(new LoginTokenPair(''));
      fixture.detectChanges();
    }));

    it('should show the success view.', () => {
      const successElement: HTMLElement = fixture.debugElement.query(By.css('.login-complete')).nativeElement;
      expect(successElement).not.toBeNull();
    });

  });

  // TODO: Add more tests.

});

const SIGN_IN_BUTTON = 'test-sign-in-button';

@Component({
  template: `
    <gae-sign-in-gateway-view [gateway]="gateway">
      <div signin>
        <button id="${SIGN_IN_BUTTON}">Click to Sign In.</button>
      </div>
    </gae-sign-in-gateway-view>
  `
})
class SignInGatewayViewComponent {

  public gateway = new TestSignInGateway();

}

class TestSignInGateway extends AbstractSignInGateway {

  public setIdle() {
    this.nextIdle();
  }

  public setWorking() {
    this.nextWorking();
  }

  public setToken(token: LoginTokenPair) {
    super.nextLoginToken(token);
  }

  public setError(error: Error) {
    this.nextError(error);
  }

}
