import { Injectable } from '@angular/core';
import { ValueUtility, ModelType } from '@gae-web/appengine-utility';

// MARK: Module
export abstract class ApiModuleInfo {
  /**
   * Server to send requests to.
   */
  readonly server?: string;
  readonly version: string;
  readonly name: string;
}

/**
 * Info configuration for an Appengine module.
 */
export class GaeApiModuleInfo implements ApiModuleInfo {
  constructor(public readonly version: string, public readonly name: string, public readonly server: string = '') { }
}

// MARK: Route
export abstract class ApiModuleRouteConfiguration {
  readonly root: string;
}

/**
 * Route configuration for an Appengine Module's appengine components.
 */
export class GaeApiModuleRouteConfiguration implements ApiModuleRouteConfiguration {

  private _fullRoute: string;

  public static makeWithInfo(info: ApiModuleInfo, prefix?: string): GaeApiModuleRouteConfiguration {
    return new GaeApiModuleRouteConfiguration(info.name + '/' + info.version, prefix, info.server);
  }

  constructor(_route: string,  _prefix: string = '/api', _server: string = '') {
    this._fullRoute = _server + _prefix + '/' + _route;
  }

  // MARK: ApiModuleRouteConfiguration
  /**
   * Returns the root api route for the api.
   */
  get root(): string {
    return this._fullRoute;
  }

}

// MARK: Types
export abstract class ApiModuleTypesConfiguration {
  readonly types: ModelType[];
  readonly typeSet: Set<ModelType>;
  abstract hasType(type: ModelType): boolean;
}

/**
 * Types configuration for an Appengine Module. May be used by other components to determine which module should be accessed for a specific model type.
 */
export class GaeApiModuleTypesConfiguration implements ApiModuleTypesConfiguration {

  private _types: ModelType[];
  private _typeSet: Set<ModelType>;

  constructor(types: ModelType[]) {
    this._types = types.map(x => x.toLowerCase());
    this._typeSet = ValueUtility.arrayToSet(this._types);
  }

  // MARK: GaeApiModuleTypesConfiguration
  public get types(): ModelType[] {
    return this._types;
  }

  public get typeSet(): Set<ModelType> {
    return this._typeSet;
  }

  public hasType(type: ModelType): boolean {
    return this._typeSet.has(type);
  }

}

/**
 * Helper interface used for ApiModuleConfiguration static constructors.
 */
export interface ApiModuleConstructorConfiguration {
  server?: string;
  version?: string;
  name?: string;
  types?: ModelType[];
}

/**
 * Contains all configuration for an Appengine Module.
 */
export class GaeApiModuleConfiguration implements ApiModuleInfo,
  ApiModuleRouteConfiguration, ApiModuleRouteConfiguration {

  constructor(public readonly info: ApiModuleInfo,
    public readonly typesConfig: ApiModuleTypesConfiguration,
    public readonly routeConfig: ApiModuleRouteConfiguration = GaeApiModuleRouteConfiguration.makeWithInfo(info)) { }

  // MARK: ApiModuleInfo
  get name(): string {
    return this.info.name;
  }

  get version(): string {
    return this.info.version;
  }

  // MARK: ApiModuleRouteConfiguration
  get root(): string {
    return this.routeConfig.root;
  }

  // MARK: ApiModuleTypesConfiguration
  public get types(): ModelType[] {
    return this.typesConfig.types;
  }

  public get typeSet(): Set<ModelType> {
    return this.typesConfig.typeSet;
  }

  public hasType(type: ModelType): boolean {
    return this.typesConfig.hasType(type);
  }

}
