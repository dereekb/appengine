import { NgModule, ModuleWithProviders } from '@angular/core';
import { GaeActionMessageSnackbarDirective } from './snackbar.directive';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { GaeColorImageComponent, GaeColorStyleDirective, GaeBackgroundColorStyleDirective, GaeStringColorDirective } from './color.component';
import { CommonModule } from '@angular/common';
import { GaePageTitleDirective, PageTitleService } from './title.component';
import { GaePageFabComponent, GaePageButtonSegueDirective, PageFabService, GaePageButtonComponent } from './fab.component';
import { UIRouterModule } from '@uirouter/angular';
import { FlexLayoutModule } from '@angular/flex-layout';
import { GaePageToolbarComponent, GaePageToolbarConfigurationComponent, GaePageToolbarNavButtonComponent, GaePageToolbarSidenavControllerLinkDirective } from './page-toolbar.component';
import { GaeAppPageComponent } from './page.component';
import { GaeSidenavControllerDirective } from './sidenav.component';
import { MatProgressButtonsModule } from 'mat-progress-buttons';

@NgModule({
  imports: [
    CommonModule,
    UIRouterModule,
    MatSnackBarModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    MatSidenavModule,
    FlexLayoutModule,
    MatProgressButtonsModule
  ],
  declarations: [
    GaeActionMessageSnackbarDirective,
    GaeColorImageComponent,
    GaeColorStyleDirective,
    GaeBackgroundColorStyleDirective,
    GaeStringColorDirective,
    GaePageTitleDirective,
    GaeAppPageComponent,
    GaePageFabComponent,
    GaePageButtonSegueDirective,
    GaePageButtonComponent,
    GaePageToolbarComponent,
    GaePageToolbarConfigurationComponent,
    GaePageToolbarNavButtonComponent,
    GaeSidenavControllerDirective,
    GaePageToolbarSidenavControllerLinkDirective
  ],
  exports: [
    GaeActionMessageSnackbarDirective,
    GaeColorImageComponent,
    GaeColorStyleDirective,
    GaeBackgroundColorStyleDirective,
    GaeStringColorDirective,
    GaePageTitleDirective,
    GaeAppPageComponent,
    GaePageFabComponent,
    GaePageButtonSegueDirective,
    GaePageButtonComponent,
    GaePageToolbarComponent,
    GaePageToolbarConfigurationComponent,
    GaeSidenavControllerDirective,
    GaePageToolbarSidenavControllerLinkDirective
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
