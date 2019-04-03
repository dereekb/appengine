import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OAuthSignInGatewayComponent } from '../components/oauth.component';
import { GoogleSignInButtonDirective, FacebookSignInButtonDirective, GoogleModule, FacebookModule, GoogleOAuthServiceConfig, FacebookApiServiceConfig } from '@gae-web/appengine-services';
import { GatewayDirectivesModule } from '../directives.module';
import { OAuthLoginApiService, TestUtility } from '@gae-web/appengine-api';
import { SignInComponent } from './signin.component';

describe('AppengineGatewayComponent', () => {
  let component: SignInComponent;
  let fixture: ComponentFixture<SignInComponent>;

  const httpClientSpy: { post: jasmine.Spy } = jasmine.createSpyObj('HttpClient', ['post']);

  const httpClient = httpClientSpy as any;
  const testOAuthLoginApiService = new OAuthLoginApiService(httpClient, TestUtility.testApiRouteConfig());

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GoogleModule.forRoot(new GoogleOAuthServiceConfig(''), false),
        FacebookModule.forRoot(new FacebookApiServiceConfig(''), false),
        GatewayDirectivesModule
      ],
      declarations: [ OAuthSignInGatewayComponent, SignInComponent ],
      providers: [{
        provide: OAuthLoginApiService,
        useValue: testOAuthLoginApiService
      }]
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

