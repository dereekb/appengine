import { AbstractDatabaseModel, AbstractDatabaseModelSerializer, DatabaseModelData } from '@gae-web/appengine-client';
import { DatedModel, NumberModelKey, ValidNumberModelKey, ModelUtility, ModelKey } from '@gae-web/appengine-utility';
import { DateTime } from 'luxon';

/**
 * Test model Foo that has a number identifier and some properties.
 */
export class Foo extends AbstractDatabaseModel implements DatedModel {

  private _key: NumberModelKey;

  public date: DateTime;

  constructor(key?: NumberModelKey) {
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
 * DTO for the Foo model.
 */
export class FooData extends DatabaseModelData {

  public static fromJson(json: any): FooData {
    const data = new FooData();
    return Object.assign(data, json);
  }

  // MARK: JSON
  public toJSON(): any {
    return Object.assign({}, this);
  }

}

export class FooSerializer
  extends AbstractDatabaseModelSerializer<Foo, FooData> {

  public convertToDto(model: Foo): FooData {
    const data = super.convertToDto(model);

    data.setDateValue(model.date);

    return data;
  }

  public convertToModel(dto: FooData): Foo {
    const model = super.convertToModel(dto);

    const date = dto.getDateValue();

    if (date) {
      model.date = date;
    }

    return model;
  }

  public convertJsonToDto(json: any): FooData {
    return FooData.fromJson(json);
  }

  protected makeModel(): Foo {
    return new Foo();
  }

  protected makeDto(): FooData {
    return new FooData();
  }

}
