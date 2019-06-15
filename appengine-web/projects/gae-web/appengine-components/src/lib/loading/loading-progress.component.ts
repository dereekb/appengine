import { Component, Input } from '@angular/core';

/**
 * Basic loading progress component.
 */
@Component({
  selector: 'gae-loading-progress',
  template: `
  <div class="loading-progress-view">
    <mat-progress-spinner *ngIf="!linear" diameter="96" mode="indeterminate" style="margin: auto;"></mat-progress-spinner>
    <mat-progress-bar *ngIf="linear" mode="indeterminate" style="margin: auto;"></mat-progress-bar>
    <div *ngIf="text" class="hint">{{ text }}</div>
  </div>
  `
})
export class GaeLoadingProgressComponent {

  @Input()
  public text: string;

  @Input()
  public linear: boolean;

}
