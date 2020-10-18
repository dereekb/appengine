import { NgModule, ModuleWithProviders } from '@angular/core';
import { ClientLinkService } from '@gae-web/appengine-api';
import { ModelLinkService } from './service/link.service';
import { ModelServiceWrapperSet } from './service/model.service';
import { GaeApiModule, GaeApiLinkService } from '@gae-web/appengine-api';

export function modelLinkServiceFactory(wrapperSet: ModelServiceWrapperSet, clientService: ClientLinkService) {
  return new ModelLinkService(wrapperSet, clientService);
}

@NgModule({
  imports: [GaeApiModule],
  exports: []
})
export class GaeClientModule {

  static forApp(): ModuleWithProviders<GaeClientModule> {
    return {
      ngModule: GaeClientModule,
      providers: [
        // Wrapper
        ModelServiceWrapperSet,
        // Link Service
        {
          provide: ModelLinkService,
          useFactory: modelLinkServiceFactory,
          deps: [ModelServiceWrapperSet, GaeApiLinkService]
        }
      ]
    };
  }

}
