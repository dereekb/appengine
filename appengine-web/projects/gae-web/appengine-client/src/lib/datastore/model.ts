import { UniqueModel, ModelKey, MutableUniqueModel } from '@gae-web/appengine-utility';

// MARK: Ownership
export type OwnerId = string;

export interface OwnedModel {
  readonly ownerId: OwnerId;
}

export interface MutableOwnedModel extends OwnedModel {
  ownerId: OwnerId;
}

export interface OwnedDatabaseModel extends MutableOwnedModel, MutableUniqueModel { }

// MARK: Searching
export type SearchId = string;

export interface SearchableDatabaseModel extends UniqueModel {
  readonly searchId: SearchId;
}

// MARK: Database
export abstract class AbstractOwnedDatabaseModel implements OwnedDatabaseModel, MutableOwnedModel {

  private _ownerId: OwnerId;

  // MARK: Abstract
  abstract get modelKey(): ModelKey;

  get key() {
    return this.modelKey;
  }

  // MARK: Ownership
  get ownerId() {
    return this._ownerId;
  }

  set ownerId(ownerId) {
    this._ownerId = ownerId;
  }

}

// MARK: Searching
export abstract class AbstractSearchableDatabaseModel extends AbstractOwnedDatabaseModel implements SearchableDatabaseModel {

  private _searchId: SearchId;

  // MARK: Search Id
  get searchId() {
    return this._searchId;
  }

  set searchId(searchId) {
    this._searchId = searchId;
  }

}
