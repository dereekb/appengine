import { async, TestBed } from '@angular/core/testing';
import { GaeLoginApiModule, GaeLoginApiModuleService, GaeLoginApiModuleConfiguration } from './module/login.api.module';
import { GaeEventApiModule, GaeEventApiModuleService, GaeEventApiModuleConfiguration } from './module/event.api.module';
import { GaeApiModule, GaeApiConfiguration } from './api.module';
import { HttpClient, HttpBackend } from '@angular/common/http';
import { ApiModuleUnavailableException } from './error';
import { OAuthLoginApiService } from './auth/oauth.service';
import { RegisterApiService } from './auth/register.service';

function gaeApiConfigurationFactory(loginService: GaeLoginApiModuleService, eventService: GaeEventApiModuleService) {
  return new GaeApiConfiguration([loginService, eventService]);
}

describe('GaeApiModule', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeLoginApiModule.forApp(),
        GaeEventApiModule.forApp(),
        GaeApiModule.forApp({
          gaeApiConfigurationProvider: {
            provide: GaeApiConfiguration,
            useFactory: gaeApiConfigurationFactory,
            deps: [GaeLoginApiModuleService, GaeEventApiModuleService]
          }
        })
      ],
      providers: [{
        provide: GaeLoginApiModuleConfiguration,
        useValue: GaeLoginApiModuleConfiguration.make({})
      }, {
        provide: GaeEventApiModuleConfiguration,
        useValue: GaeEventApiModuleConfiguration.make({})
      }, {
        provide: HttpClient,
        useValue: {} as any
      },
      {
        provide: HttpBackend,
        useValue: {} as any
      }]
    });
  }));

  describe('GaeApiConfiguration', () => {

    it('should provide a GaeApiConfiguration', () => {
      const configuration: GaeApiConfiguration = TestBed.get(GaeApiConfiguration);
      expect(configuration).toBeDefined();
    });

    it('should return the service with the "login" type', () => {
      const configuration: GaeApiConfiguration = TestBed.get(GaeApiConfiguration);
      const service = configuration.getServiceForType('login');
      expect(service).toBeDefined();
    });

    it('should thrown an exception for unavailable service types', () => {
      const configuration: GaeApiConfiguration = TestBed.get(GaeApiConfiguration);

      try {
        configuration.getServiceForType('doesNotExist');
        fail();
      } catch (e) {
        expect(e).toEqual(jasmine.any(ApiModuleUnavailableException));
      }
    });

  });

  describe('GaeLoginApiModule', () => {

    it('should provide a OAuthLoginApiService', () => {
      const configuration: OAuthLoginApiService = TestBed.get(OAuthLoginApiService);
      expect(configuration).toBeDefined();
    });

    it('should provide a RegisterApiService', () => {
      const configuration: RegisterApiService = TestBed.get(RegisterApiService);
      expect(configuration).toBeDefined();
    });

  });

});
