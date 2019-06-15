import { ChangeDetectorRef, ViewRef } from '@angular/core';

export class GaeViewUtility {

  static safeDetectChanges(cdRef: ChangeDetectorRef) {
    if (!(cdRef as ViewRef).destroyed) {
      cdRef.detectChanges();
    }
  }

}
