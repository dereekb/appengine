import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';

import { HttpResponse } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';

import { AbstractClientService, ClientServiceConfig } from '../../client.service';
import { ApiResponseJson } from '../../../api';
import { ScheduleResponse } from '../scheduler/scheduler.service';
import { ClientApiResponse } from '../../client';

import { DateTime } from 'luxon';

export type NotificationDeviceUUID = string;
export type NotificationDeviceToken = string;

export abstract class UserNotificationService {
  abstract addDevice(request: NotificationDeviceRequest): Observable<NotificationDeviceResponse>;
}

export interface NotificationDevice {
  device: NotificationDeviceUUID;
  token: NotificationDeviceToken;
}

export interface NotificationDeviceRequest extends NotificationDevice {
  date?: DateTime;
}

export interface NotificationDeviceResponse {
  readonly success: boolean;
}

/**
 * UserNotificationService implementation
 */
export class ClientUserNotificationService extends AbstractClientService implements UserNotificationService {

  constructor(config: ClientServiceConfig) {
    super(config);
  }

  // MARK: UserNotificationService
  public addDevice(request: NotificationDeviceRequest): Observable<NotificationDeviceResponse> {
    if (this.isValidScheduleRequest(request)) {
      const url = this.rootPath + '/notification/device/add';

      const body = this.makeRequestData(request);

      const obs = this.httpClient.put<ApiResponseJson>(url, body, {
        observe: 'response'
      });

      return this.handleScheduleResponse(request, obs);
    } else {
      return throwError(new Error('Invalid schedule request.'));
    }
  }

  protected isValidScheduleRequest(request: NotificationDeviceRequest) {
    return request.device && request.token;
  }

  protected makeRequestData(request: NotificationDeviceRequest): {} {
    const data = {
      device: request.device,
      token: request.token
    };

    return data;
  }

  protected handleScheduleResponse(request: NotificationDeviceRequest, obs: Observable<HttpResponse<ApiResponseJson>>): Observable<ScheduleResponse> {
    return super.handleResponse(obs).pipe(
      map((response: ClientApiResponse) => {
        return {
          success: true
        };
      })
    );
  }

}
