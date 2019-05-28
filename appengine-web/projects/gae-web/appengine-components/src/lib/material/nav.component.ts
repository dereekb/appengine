import { Input, Component } from '@angular/core';

// MARK: Interfaces
export interface ClickableFunction {
  onClick?: () => void;
}

export interface ClickableSegue {
  ref?: string;
}

export interface ClickableElement extends ClickableSegue, ClickableFunction { }

export interface IconElement {
  icon?: string;
}

export interface DisableableElement {
  disabled?: boolean;
}

export interface TitledElement extends IconElement, DisableableElement {
  text?: string;
}

export interface NavigationLink extends TitledElement, ClickableSegue { }

export interface ClickableButton extends TitledElement, ClickableElement, DisableableElement { }
