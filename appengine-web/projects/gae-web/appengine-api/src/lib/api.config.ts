import { Injectable } from '@angular/core';

/**
 * Info configuration for the Appengine module.
 */
export class AppengineApiModuleInfo {
  version: string;
  name: string;
}

/**
 * Route configuration for all Appengine components.
 */
export class AppengineApiRouteConfiguration {

  private _fullRoute: string;

  public static makeWithInfo(info: AppengineApiModuleInfo, prefix?: string): AppengineApiRouteConfiguration {
    return new AppengineApiRouteConfiguration(info.name + '/' + info.version, prefix);
  }

  constructor(private _route: string, private _prefix: string = '/api') {
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
export class AppengineApiConfiguration {

  constructor(private _info: AppengineApiModuleInfo, private _route: AppengineApiRouteConfiguration) { }

  get info(): AppengineApiModuleInfo {
    return this._info;
  }

  get routeConfig(): AppengineApiRouteConfiguration {
    return this._route;
  }

}
