import { NgModule, ModuleWithProviders } from '@angular/core';
import { GaeModelComponentsModule } from './model/model.module';
import { GaeFormComponentsModule } from './form/form.module';
import { GaeListComponentsModule } from './list/list.module';
import { GaeSelectionListComponentsModule } from './list/selection.module';
import { GaeLoadingComponentsModule } from './loading/loading.module';
import { GaeMaterialComponentsModule } from './material/material.module';
import { GaeSegueComponentsModule } from './state/segue.module';

/**
 * Module that re-exports all Gae Component modules.
 */
@NgModule({
  exports: [
    GaeFormComponentsModule,
    GaeListComponentsModule,
    GaeSelectionListComponentsModule,
    GaeLoadingComponentsModule,
    GaeModelComponentsModule,
    GaeMaterialComponentsModule,
    GaeSegueComponentsModule
  ]
})
export class GaeComponentsModule {

  static forApp(): ModuleWithProviders {
    return {
      ngModule: GaeComponentsModule,
      providers: []
    };
  }

}

/**
 * Pre-configured GaeComponentsModule that imports GaeComponentsModule and GaeMaterialComponentsModule.
 */
@NgModule({
  imports: [
    GaeComponentsModule.forApp(),
    GaeMaterialComponentsModule.forApp()
  ]
})
export class GaeComponentsPreConfiguredAppModule { }
