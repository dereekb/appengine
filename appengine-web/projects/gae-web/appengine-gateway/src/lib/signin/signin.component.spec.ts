import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SignInComponent } from './signin.component';
import { GatewayMaterialModule } from '../material.module';
import { GatewayDirectivesModule } from '../directives.module';
import { OAuthSignInGatewayComponent } from '../components/oauth.component';

describe('AppengineGatewayComponent', () => {
  let component: SignInComponent;
  let fixture: ComponentFixture<SignInComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ GatewayDirectivesModule, GatewayMaterialModule ],
      declarations: [ OAuthSignInGatewayComponent, SignInComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignInComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});

