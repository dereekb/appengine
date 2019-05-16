import { NgModule, ModuleWithProviders } from '@angular/core';
import { GaeActionMessageSnackbarDirective } from './snackbar.directive';
import { MatSnackBarModule, MatButtonModule, MatIconModule } from '@angular/material';
import { GaeColorImageComponent, GaeColorStyleDirective, GaeBackgroundColorStyleDirective, GaeStringColorDirective } from './color.component';
import { CommonModule } from '@angular/common';
import { GaePageTitleDirective, PageTitleService } from './title.component';
import { GaePageFabComponent, GaePageButtonSegueDirective, PageFabService, GaePageButtonComponent } from './fab.component';
import { UIRouterModule } from '@uirouter/angular';
import { FlexLayoutModule } from '@angular/flex-layout';

@NgModule({
  imports: [
    CommonModule,
    UIRouterModule,
    MatSnackBarModule,
    MatButtonModule,
    MatIconModule,
    FlexLayoutModule
  ],
  declarations: [
    GaeActionMessageSnackbarDirective,
    GaeColorImageComponent,
    GaeColorStyleDirective,
    GaeBackgroundColorStyleDirective,
    GaeStringColorDirective,
    GaePageTitleDirective,
    GaePageFabComponent,
    GaePageButtonSegueDirective,
    GaePageButtonComponent
  ],
  exports: [
    GaeActionMessageSnackbarDirective,
    GaeColorImageComponent,
    GaeColorStyleDirective,
    GaeBackgroundColorStyleDirective,
    GaeStringColorDirective,
    GaePageTitleDirective,
    GaePageFabComponent,
    GaePageButtonSegueDirective,
    GaePageButtonComponent
  ]
})
export class GaeMaterialComponentsModule {

  static forApp(): ModuleWithProviders {
    return {
      ngModule: GaeMaterialComponentsModule,
      providers: [
        PageFabService,
        PageTitleService
      ]
    };
  }

}
