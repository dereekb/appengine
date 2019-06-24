import { ClientLinkService, LinkService, ClientLinkResponse } from './model/extension/link/link.service';
import { ClientSchedulerService, SchedulerService, ScheduleRequest, ScheduleResponse } from './model/extension/scheduler/scheduler.service';
import { LinkRequest } from './model/extension/link/link';
import { Observable } from 'rxjs';
import { ClientServiceConfig } from './model/client.service';
import { GaeApiModuleConfiguration } from './api.config';
import { HttpClient } from '@angular/common/http';
import { ModelType } from '@gae-web/appengine-utility/lib/model';

/**
 * Exposes API-module-wide services.
 *
 * Each api module should implement their own class for dependency-injection purposes.
 */
export abstract class ApiModuleService implements LinkService, SchedulerService {

  public readonly linkService: ClientLinkService;
  public readonly schedulerService: ClientSchedulerService;

  constructor(public readonly moduleConfig: GaeApiModuleConfiguration, httpClient: HttpClient) {
    const clientConfig: ClientServiceConfig = {
      httpClient,
      routeConfig: moduleConfig.routeConfig
    };

    this.linkService = new ClientLinkService(clientConfig);
    this.schedulerService = new ClientSchedulerService(clientConfig);
  }

  // MARK: ApiModuleService
  public get name() {
    return this.moduleConfig.info.name;
  }

  public hasType(type: ModelType): boolean {
    return this.moduleConfig.hasType(type);
  }

  public get types(): ModelType[] {
    return this.moduleConfig.types;
  }

  public get typeSet(): Set<ModelType> {
    return this.moduleConfig.typeSet;
  }

  // MARK: Link Service
  public updateLinks(request: LinkRequest): Observable<ClientLinkResponse> {
    return this.linkService.updateLinks(request);
  }

  // MARK: SchedulerService
  public schedule(request: ScheduleRequest): Observable<ScheduleResponse> {
    return this.schedulerService.schedule(request);
  }

}
