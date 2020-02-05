import { ClickableSegue, ClickableElement } from '../shared/clickable';

// MARK: Interfaces
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
