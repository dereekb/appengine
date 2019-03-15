import { TestBed } from '@angular/core/testing';

import { AppengineClientService } from './appengine-client.service';

describe('AppengineClientService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AppengineClientService = TestBed.get(AppengineClientService);
    expect(service).toBeTruthy();
  });
});
