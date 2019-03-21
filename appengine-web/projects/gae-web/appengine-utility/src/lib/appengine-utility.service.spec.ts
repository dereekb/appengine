import { TestBed } from '@angular/core/testing';

import { AppengineUtilityService } from './appengine-utility.service';

describe('AppengineUtilityService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AppengineUtilityService = TestBed.get(AppengineUtilityService);
    expect(service).toBeTruthy();
  });
});
