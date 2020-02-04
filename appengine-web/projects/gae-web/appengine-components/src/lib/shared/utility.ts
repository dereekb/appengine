import { ChangeDetectorRef, ViewRef, ElementRef } from '@angular/core';

export class GaeViewUtility {

  static safeDetectChanges(cdRef: ChangeDetectorRef) {
    if (!(cdRef as ViewRef).destroyed) {
      cdRef.detectChanges();
    }
  }

  /**
   * Used to check an injected ElementRef that wraps an ng-content injection point whether or not any content was injected,
   * or more specifically if the parent component passed any target content to the child. This will still return true if
   * passed content is empty.
   *
   * TS:
   * @ViewChild('customLoading', { static: false }) customCustom: ElementRef;
   *
   * HTML:
   * <div #customContent>
   *  <ng-content select="[content]"></ng-content>
   * </div>
   *
   */
  static checkNgContentWrapperHasContent(ref: ElementRef<Element> | undefined): boolean {
    let hasContent = false;

    if (ref) {
      const childNodes = ref.nativeElement.childNodes;
      const hasChildNodes = childNodes && childNodes.length > 0;
      hasContent = Boolean(hasChildNodes);
    }

    return hasContent;
  }

}
