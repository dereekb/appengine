import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, Input, DebugElement, ViewChild } from '@angular/core';
import { GaeModelModule } from './model.module';
import { ProvideReadSourceComponent, AbstractReadSourceComponent, ReadSourceModelKeyDirective, ReadSourceComponent } from './read.component';
import { ReadSourceFactory } from '@gae-web/appengine-client';
import { AbstractDatabaseModel, ReadRequest, ReadResponse, ReadService } from '@gae-web/appengine-api';
import { Observable, of } from 'rxjs';
import { ModelUtility, ModelKey, ValueUtility } from '@gae-web/appengine-utility';
import { By } from '@angular/platform-browser';
import { DebugContext } from '@angular/core/src/view';

describe('Read Components', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [GaeModelModule],
      declarations: [TestReadSourceKeyComponent, TestReadSourceComponent]
    }).compileComponents();
  }));

  describe('ReadSourceModelKeyDirective', () => {
    let fixture: ComponentFixture<TestReadSourceKeyComponent>;
    let component: TestReadSourceKeyComponent;

    let testReadSourceComponentComponent: TestReadSourceComponent;
    let readSourceDirectiveComponent: ReadSourceModelKeyDirective<TestModel>;

    beforeEach(async(() => {
      fixture = TestBed.createComponent(TestReadSourceKeyComponent);
      component = fixture.componentInstance;

      const testReadSourceComponentElement: DebugElement = fixture.debugElement.query(By.directive(TestReadSourceComponent));
      testReadSourceComponentComponent = testReadSourceComponentElement.componentInstance;

      readSourceDirectiveComponent = component.gaeReadSource;

      fixture.detectChanges();
    }));

    it('should have the key value.', () => {
      expect(readSourceDirectiveComponent.key).toBe(component.key);
    });

    it('should have bound to the host ReadSourceComponent', () => {
      expect(readSourceDirectiveComponent.source).toBeDefined();
      expect(readSourceDirectiveComponent.source).toBe(testReadSourceComponentComponent);
    });

    it('should have set the readSourceKeys value on the ReadSourceComponent', () => {
      expect(testReadSourceComponentComponent.readSourceKeys).toBeDefined();
    });

  });

});

export class TestModel extends AbstractDatabaseModel {
  constructor(public modelKey: ModelKey) {
    super();
  }
}

export class TestModelReadService implements ReadService<TestModel> {

  readonly type = 'Test';

  read(request: ReadRequest): Observable<ReadResponse<TestModel>> {
    const readResponse: ReadResponse<TestModel> = {
      models: ValueUtility.normalizeArray(request.modelKeys).map(x => new TestModel(x)),
      failed: []
    };

    return of(readResponse);
  }

}

export class TestReadSource extends ReadSourceFactory<TestModel> {

  constructor() {
    super(new TestModelReadService());
  }

}

@Component({
  template: '',
  selector: 'gae-test-read-source',
  providers: [ProvideReadSourceComponent(TestReadSourceComponent)]
})
export class TestReadSourceComponent extends AbstractReadSourceComponent<TestModel> {

  constructor() {
    super(new TestReadSource());
  }

}

@Component({
  template: `
    <gae-test-read-source [gaeReadSourceKey]="key"></gae-test-read-source>
  `
})
class TestReadSourceKeyComponent {

  key: ModelKey = 1;

  @ViewChild(ReadSourceModelKeyDirective)
  public gaeReadSource: ReadSourceModelKeyDirective<TestModel>;

}
