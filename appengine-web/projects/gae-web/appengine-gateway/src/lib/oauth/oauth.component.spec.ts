import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OAuthSignInGatewayComponent } from './oauth.component';
import { GaeGoogleModule, GaeFacebookModule, GoogleOAuthServiceConfig, FacebookApiServiceConfig } from '@gae-web/appengine-services';
import { OAuthLoginApiService, TestUtility } from '@gae-web/appengine-api';
import { UserLoginTokenService, LegacyAppTokenUserService, StoredTokenStorageAccessor, UserLoginTokenAuthenticator, AppTokenStorageService } from '@gae-web/appengine-token';
import { GaeGatewayOAuthModule } from './oauth.module';

describe('OAuthSignInGatewayComponent', () => {
  let component: OAuthSignInGatewayComponent;
  let fixture: ComponentFixture<OAuthSignInGatewayComponent>;

  const httpClientSpy: { post: jasmine.Spy } = jasmine.createSpyObj('HttpClient', ['post']);

  const httpClient = httpClientSpy as any;
  const testOAuthLoginApiService = new OAuthLoginApiService(httpClient, TestUtility.testApiRouteConfig());

  const storageAccessor = StoredTokenStorageAccessor.getLocalStorageOrBackupAccessor();
  const tokenAuthenticator: UserLoginTokenAuthenticator = {} as any;

  const testUserLoginTokenService = new LegacyAppTokenUserService(new AppTokenStorageService(storageAccessor), tokenAuthenticator);

  beforeEach(async(() => {

    TestBed.configureTestingModule({
      imports: [
        GaeGoogleModule.forRoot(new GoogleOAuthServiceConfig(''), false),
        GaeFacebookModule.forRoot(new FacebookApiServiceConfig(''), false),
        GaeGatewayOAuthModule
      ],
      providers: [{
        provide: OAuthLoginApiService,
        useValue: testOAuthLoginApiService
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

