import { DatedModel, NumberModelKey, ValidNumberModelKey, ModelUtility, ModelKey } from '@gae-web/appengine-utility';
import { DateTime } from 'luxon';
import { TestReadService } from './model';
import { AbstractDatabaseModel } from '../lib/datastore/model';
import { DatabaseModelData, AbstractDatabaseModelSerializer } from '../lib/datastore/data';

export const TEST_FOO_MODEL_TYPE = 'foo';

/**
 * Test model TestFoo that has a number identifier and some properties.
 */
export class TestFoo extends AbstractDatabaseModel implements DatedModel {

  private _key: NumberModelKey;

  public date: DateTime;

  constructor(key?: NumberModelKey, public name?: string) {
    super();

    if (key) {
      this.key = key;
    }
  }

  // MARK: Accessors
  get key(): NumberModelKey {
    return this._key;
  }

  @ValidNumberModelKey()
  set key(key: NumberModelKey) {
    this._key = key;
  }

  // MARK: MutableDatabaseModel
  get modelKey() {
    return this._key;
  }

  set modelKey(key: ModelKey) {
    this.key = ModelUtility.makeNumberModelKey(key);
  }

}

/**
 * DTO for the TestFoo model.
 */
export class TestFooData extends DatabaseModelData {

  public static fromJson(json: any): TestFooData {
    const data = new TestFooData();
    return Object.assign(data, json);
  }

  constructor(key?: ModelKey) {
    super(key);
  }

  // MARK: JSON
  public toJSON(): any {
    return Object.assign({}, this);
  }

}

export class TestFooSerializer
  extends AbstractDatabaseModelSerializer<TestFoo, TestFooData> {

  public convertToDto(model: TestFoo): TestFooData {
    const data = super.convertToDto(model);

    data.setDateValue(model.date);

    return data;
  }

  public convertToModel(dto: TestFooData): TestFoo {
    const model = super.convertToModel(dto);
    const date = dto.getDateValue();

    if (date) {
      model.date = date;
    }

    return model;
  }

  public convertJsonToDto(json: any): TestFooData {
    return TestFooData.fromJson(json);
  }

  protected makeModel(): TestFoo {
    return new TestFoo();
  }

  protected makeDto(): TestFooData {
    return new TestFooData();
  }

}

// MARK: Services
export class TestFooReadService extends TestReadService<TestFoo> {

  constructor() {
    super(TEST_FOO_MODEL_TYPE, (x) => new TestFoo(x as NumberModelKey));
  }

}
