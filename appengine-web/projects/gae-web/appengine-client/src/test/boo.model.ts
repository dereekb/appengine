import { AbstractDatabaseModel } from '@gae-web/appengine-client';
import { DatedModel, StringModelKey, AssertValidStringModelKey, ModelUtility, ModelKey } from '@gae-web/appengine-utility';
import { DateTime } from 'luxon';

/**
 * Test model Foo that has a number identifier and some properties.
 */
export class BooTestModel extends AbstractDatabaseModel implements DatedModel {

  private _key: StringModelKey;

  public date: DateTime;

  constructor(key?: StringModelKey) {
    super();
    if (key) {
      this.key = key;
    }
  }

  // MARK: Accessors
  get key(): StringModelKey {
    return this._key;
  }

  @AssertValidStringModelKey()
  set key(key: StringModelKey) {
    this._key = key;
  }

  // MARK: MutableDatabaseModel
  get modelKey() {
    return this._key;
  }

  set modelKey(key: ModelKey) {
    this.key = ModelUtility.makeStringModelKey(key);
  }

}
