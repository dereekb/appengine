import { DatabaseModelData, DescribedDatabaseModelData, AbstractDescribedDatabaseModel } from '@gae-web/appengine-api';
import { NumberModelKey, ValidNumberModelKey, ModelKey, ModelUtility } from '@gae-web/appengine-utility';
import { DateTime } from 'luxon';

export const FOO_MODEL_TYPE = 'foo';

export class Foo extends AbstractDescribedDatabaseModel {

  private _key: NumberModelKey;

  public date: DateTime;

  public name: string;
  public number: number;
  public numberList: number[];
  public stringSet: Set<string>;

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
