import { RawParams, TransitionOptions } from '@uirouter/core';

export interface ClickableFunction {
  onClick?: (event?: MouseEvent) => void;
}

export interface ClickableSegue {

  /**
   * UI Sref reference value.
   */
  ref?: string;

}

export interface ConfigurableClickableSegue extends ClickableSegue {

  /**
   * UI Sref parameters. Typically passed to uiParams.
   */
  refParams?: RawParams;

  /**
   * UI Sref Transition Options. Typically passed to uiOptions.
   */
  refOptions?: TransitionOptions;

}

export interface ClickableUrl {
  url?: string;
}

export interface ClickableElement extends ClickableSegue, ClickableFunction { }
