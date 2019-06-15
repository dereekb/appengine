import { Injectable } from '@angular/core';

/**
 * Info configuration for the Appengine module.
 */
export class ApiModuleInfo {
  version: string;
  name: string;
}

/**
 * Route configuration for all Appengine components.
 */
export class ApiRouteConfiguration {

  private _fullRoute: string;

  public static makeWithInfo(info: ApiModuleInfo, prefix?: string): ApiRouteConfiguration {
    return new ApiRouteConfiguration(info.name + '/' + info.version, prefix);
  }

  constructor(_route: string, _prefix: string = '/api') {
    this._fullRoute = _prefix + '/' + _route;
  }

  /**
   * Returns the root api route for the api.
   */
  get root(): string {
    return this._fullRoute;
  }

}

/**
 * Contains all configuration for Appengine components.
 */
@Injectable()
export class ApiConfiguration {

  constructor(private _info: ApiModuleInfo, private _route: ApiRouteConfiguration) { }

  get info(): ApiModuleInfo {
    return this._info;
  }

  get routeConfig(): ApiRouteConfiguration {
    return this._route;
  }

}
