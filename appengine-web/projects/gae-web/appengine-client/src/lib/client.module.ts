import { NgModule, ModuleWithProviders } from '@angular/core';
import { ApiModule, ClientLinkService } from '@gae-web/appengine-api';
import { ModelLinkService } from './service/link.service';
import { ModelServiceWrapperSet } from './service/model.service';

export function modelLinkServiceFactory(wrapperSet: ModelServiceWrapperSet, clientService: ClientLinkService) {
  return new ModelLinkService(wrapperSet, clientService);
}

@NgModule({
  declarations: [],
  imports: [ApiModule],
  exports: []
})
export class ClientModule {

  static forApp(): ModuleWithProviders {
    return {
      ngModule: ClientModule,
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
