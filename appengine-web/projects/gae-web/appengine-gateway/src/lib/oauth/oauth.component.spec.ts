import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OAuthSignInGatewayComponent } from './oauth.component';
import { GaeGoogleModule, GaeFacebookModule, GoogleOAuthServiceConfig, FacebookApiServiceConfig } from '@gae-web/appengine-services';
import { OAuthLoginApiService, TestUtility } from '@gae-web/appengine-api';
import { UserLoginTokenService, BasicTokenUserService, StoredTokenStorageAccessor, UserLoginTokenAuthenticator, AppTokenStorageService } from '@gae-web/appengine-token';
import { GaeGatewayOAuthModule } from './oauth.module';

describe('OAuthSignInGatewayComponent', () => {
  let component: OAuthSignInGatewayComponent;
  let fixture: ComponentFixture<OAuthSignInGatewayComponent>;

  const httpClientSpy: { post: jasmine.Spy } = jasmine.createSpyObj('HttpClient', ['post']);

  const httpClient = httpClientSpy as any;
  const testOAuthLoginApiService = new OAuthLoginApiService(httpClient, TestUtility.testApiRouteConfig());

  const testUserLoginTokenService = new BasicTokenUserService();

  beforeEach(async(() => {

    TestBed.configureTestingModule({
      imports: [
        GaeGoogleModule.forRoot(false),
        GaeFacebookModule.forRoot(false),
        GaeGatewayOAuthModule
      ],
      providers: [{
        provide: OAuthLoginApiService,
        useValue: testOAuthLoginApiService
      },
      {
        provide: GoogleOAuthServiceConfig,
        useValue: new GoogleOAuthServiceConfig('')
      },
      {
        provide: FacebookApiServiceConfig,
        useValue: new FacebookApiServiceConfig('')
      },
      {
        provide: UserLoginTokenService,
        useValue: testUserLoginTokenService
      }]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OAuthSignInGatewayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});

