import { ModelKey, UniqueModel, ModelUtility } from '@gae-web/appengine-utility';

export enum WrapperEventType {
  ModelCreate,
  ModelUpdate,
  ModelDelete,
  ModelLink
}

export type WrapperEventFilter = (wrapperEvent: ModelWrapperEvent) => boolean;

export interface AnonymousWrapperEvent {

  readonly eventType: WrapperEventType;

  readonly request?: {};
  readonly response?: {};

  readonly models?: any[];
  readonly keys?: ModelKey[];

}

export interface ModelWrapperEvent extends AnonymousWrapperEvent {
  readonly modelType: string;
}

export class WrapperEvent<T extends UniqueModel> implements ModelWrapperEvent {

  public readonly modelType;
  public readonly eventType;

  public readonly request;
  public readonly response;

  public readonly models?: T[];

  static make<T extends UniqueModel>(event: ModelWrapperEvent): ModelWrapperEvent {
    if (event.models) {
      return new WrapperEvent<T>(event);
    } else {
      return event;
    }
  }

  constructor({ modelType, eventType, request, response, models }: ModelWrapperEvent) {
    this.modelType = modelType;
    this.eventType = eventType;

    this.request = request;
    this.response = response;

    this.models = models;
  }

  public get keys(): ModelKey[] | undefined {
    if (this.models) {
      return ModelUtility.readModelKeys(this.models);
    } else {
      return undefined;
    }
  }

}

export interface ModelServiceAnonymousWrapperEventSystem {

  nextEvent(event: AnonymousWrapperEvent);

}
