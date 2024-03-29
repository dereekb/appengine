import { Injectable } from '@angular/core';

import { ClientAtomicOperationError } from '../../crud/error';
import { AbstractClientService, ClientServiceConfig } from '../../client.service';
import { Observable, throwError } from 'rxjs';
import { ApiResponseJson } from '../../../api';
import { HttpResponse } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { ClientApiResponse } from '../../client';

export abstract class SchedulerService {
    abstract schedule(request: ScheduleRequest): Observable<ScheduleResponse>;
}

export interface ScheduleRequest {
    readonly taskEntryName: string;
}

export interface ScheduleResponse {
    readonly success: boolean;
}

/**
 * SchedulerService implementation
 */
@Injectable()
export class ClientSchedulerService extends AbstractClientService implements SchedulerService {

    constructor(config: ClientServiceConfig) {
        super(config);
    }

    // MARK: ClientSchedulerService
    public schedule(request: ScheduleRequest): Observable<ScheduleResponse> {
        if (this.isValidScheduleRequest(request)) {
            const url = this.rootPath + '/scheduler/schedule';

            const body = this.makeRequestData(request);

            const obs = this.httpClient.post<ApiResponseJson>(url, body, {
                observe: 'response'
            });

            return this.handleScheduleResponse(request, obs);
        } else {
            return throwError(new Error('Invalid schedule request.'));
        }
    }

    protected isValidScheduleRequest(request: ScheduleRequest) {
        return request.taskEntryName.length > 0;
    }

    protected makeRequestData(request: ScheduleRequest): {} {
        const data = {
            taskEntryName: request.taskEntryName
        };

        return data;
    }

    protected handleScheduleResponse(request: ScheduleRequest, obs: Observable<HttpResponse<ApiResponseJson>>): Observable<ScheduleResponse> {
        return super.handleResponse(obs).pipe(
          map((response: ClientApiResponse) => {
            return {
              success: true
            };
          })
        );
    }

}
