import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OAuthSignInGatewayComponent } from '../components/oauth.component';
import { GoogleModule, FacebookModule, GoogleOAuthServiceConfig, FacebookApiServiceConfig } from '@gae-web/appengine-services';
import { GatewayComponentsModule } from './components.module';
import { OAuthLoginApiService, TestUtility } from '@gae-web/appengine-api';
import { UserLoginTokenService, LegacyAppTokenUserService, StoredTokenStorageAccessor, UserLoginTokenAuthenticator, AppTokenStorageService } from '@gae-web/appengine-token';

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
        GoogleModule.forRoot(new GoogleOAuthServiceConfig(''), false),
        FacebookModule.forRoot(new FacebookApiServiceConfig(''), false),
        GatewayComponentsModule
      ],
      declarations: [OAuthSignInGatewayComponent],
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

