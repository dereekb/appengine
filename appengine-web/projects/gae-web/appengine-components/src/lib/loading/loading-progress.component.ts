import { Component, Input } from '@angular/core';

/**
 * Basic loading progress component.
 */
@Component({
  selector: 'gae-loading-progress',
  template: `
  <div class="loading-progress-view">
    <mat-progress-spinner *ngIf="linear" diameter="96"></mat-progress-spinner>
    <mat-progress-bar *ngIf="linear" mode="indeterminate"></mat-progress-bar>
    <div *ngIf="text" class="hint text-center">{{ $ctrl.text }}</div>
  </div>
  `
})
export class GaeLoadingProgressComponent {

  @Input()
  public text: string;

  @Input()
  public linear: boolean;

}
