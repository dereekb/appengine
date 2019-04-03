import { TestBed } from '@angular/core/testing';

import { AppengineGatewayService } from './appengine-gateway.service';

describe('AppengineGatewayService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AppengineGatewayService = TestBed.get(AppengineGatewayService);
    expect(service).toBeTruthy();
  });
});
