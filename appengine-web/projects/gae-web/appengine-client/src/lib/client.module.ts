import { NgModule, ModuleWithProviders } from '@angular/core';
import { GaeApiModule, ClientLinkService } from '@gae-web/appengine-api';
import { ModelLinkService } from './service/link.service';
import { ModelServiceWrapperSet } from './service/model.service';

export function modelLinkServiceFactory(wrapperSet: ModelServiceWrapperSet, clientService: ClientLinkService) {
  return new ModelLinkService(wrapperSet, clientService);
}

@NgModule({
  declarations: [],
  imports: [GaeApiModule],
  exports: []
})
export class GaeClientModule {

  static forApp(): ModuleWithProviders {
    return {
      ngModule: GaeClientModule,
      providers: [
        // Wrapper
        ModelServiceWrapperSet,
        // Link Service
        {
          provide: ModelLinkService,
          useFactory: modelLinkServiceFactory,
          deps: [ModelServiceWrapperSet, ClientLinkService]
        }
      ]
    };
  }

}
