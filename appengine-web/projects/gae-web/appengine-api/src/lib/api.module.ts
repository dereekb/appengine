import { NgModule, ModuleWithProviders, Injectable, Inject, InjectionToken } from '@angular/core';
import { Provider } from '@angular/compiler/src/compiler_facade_interface';
import { ApiModuleService } from './api.service';
import { LinkService } from './model/extension/link/link.service';
import { ModelType, ValueUtility } from '@gae-web/appengine-utility';
import { Observable, throwError } from 'rxjs';
import { ApiModuleUnavailableException } from './error';
import { LinkResponse, LinkRequest } from './model/extension/link/link';

export class GaeApiConfiguration {

  private _typesMap: Map<ModelType, ApiModuleService>;

  constructor(public readonly apiServices: ApiModuleService[]) {
    this._typesMap = ValueUtility.mapFromArray(apiServices, (x) => x.types);
  }

  getServiceForType(type: ModelType): ApiModuleService {
    const service: ApiModuleService = this._typesMap.get(type.toLowerCase());

    if (!service) {
      throw new ApiModuleUnavailableException(`The service for model type "${type}" is unavailable. Check that it is requested.`);
    }

    return service;
  }

}

/**
 * Unified LinkService implementation that switches handlers based off of the type being addressed.
 */
@Injectable()
export class GaeApiLinkService implements LinkService {

  constructor(private readonly _apiConfiguration: GaeApiConfiguration) { }

  // MARK: LinkService
  updateLinks(request: LinkRequest): Observable<LinkResponse> {
    const service: ApiModuleService = this._apiConfiguration.getServiceForType(request.type);

    if (!service) {
      return throwError(new Error(`The specified model type ${request.type} is not registered with any API service.`));
    }

    return service.updateLinks(request);
  }

}

export interface GaeApiModuleOptions {

  /**
   * Provides a GaeApiConfiguration value.
   */
  gaeApiConfigurationProvider: Provider;

}

/**
 * Configuration for the API.
 */
@NgModule()
export class GaeApiModule {

  static forApp(options: GaeApiModuleOptions): ModuleWithProviders {
    return {
      ngModule: GaeApiModule,
      providers: [
        // Configuration
        options.gaeApiConfigurationProvider,
        // GaeApiLinkService
        GaeApiLinkService
      ]
    };
  }

}
