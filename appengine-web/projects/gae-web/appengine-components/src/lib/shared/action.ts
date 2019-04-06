import { Observable } from 'rxjs';

export enum ActionState {

  /**
   * Action was reset from complete stage.
   */
  Reset = 0,

  /**
   * Action is in progress.
   */
  Working = 1,

  /**
   * Action is completed.
   */
  Complete = 2,

  /**
   * Action encountered an error.
   */
  Error = 3

}

export type ActionFactory<O> = () => Observable<O>;

export type HandleActionResult<O, E> = (result: O) => E;

export type HandleActionError = (error: any) => void;

/**
 * Event passed by an ActionObject.
 */
export interface ActionEvent {

  readonly state: ActionState;

  readonly error?: Error;

}

/**
 * Object that has a primary action, and a single active state.
 */
export interface ActionObject {

  /**
   * ActionEvent stream for this ActionObject.
   */
  readonly stream: Observable<ActionEvent>;

  /**
   * The current state.
   */
  readonly state: ActionState;

  /**
   * Whether or not this ActionObject is available to do more work.
   */
  readonly canWork: boolean;

  /**
   * Whether or not the action is currently working.
   */
  readonly isWorking: boolean;

  /**
   * Resets the ActionObject back to it's initial state.
   */
  reset(): void;

}

/**
 * ActionObject with a typed event.
 */
export interface TypedActionObject<E extends ActionEvent> extends ActionObject {

  readonly stream: Observable<E>;

}
