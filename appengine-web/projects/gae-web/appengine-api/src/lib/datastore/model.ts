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

// MARK: Described
export interface Descriptor {
  descriptorType: string;
  descriptorId: string;
}

export interface DescribedModel {
  descriptor: Descriptor;
}

export interface UniqueDescribedModel extends UniqueModel, DescribedModel { }

// MARK: Database
export abstract class AbstractDatabaseModel {

  // MARK: Abstract
  abstract get modelKey(): ModelKey;

  get key() {
    return this.modelKey;
  }

}

export abstract class AbstractOwnedDatabaseModel extends AbstractDatabaseModel implements MutableOwnedModel {

  private _ownerId: OwnerId;

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

// MARK: Described
export abstract class AbstractDescribedDatabaseModel extends AbstractSearchableDatabaseModel implements UniqueDescribedModel {

  private _descriptorType: string;
  private _descriptorId: string;

  get descriptorType() {
    return this._descriptorType;
  }

  set descriptorType(descriptorType) {
    this._descriptorType = descriptorType;
  }

  get descriptorId() {
    return this._descriptorId;
  }

  set descriptorId(descriptorId) {
    this._descriptorId = descriptorId;
  }

  get descriptor() {
    return {
      descriptorType: this._descriptorType,
      descriptorId: this._descriptorId
    };
  }

  set descriptor(descriptor: Descriptor) {
    descriptor = descriptor || {} as any;
    this._descriptorType = descriptor.descriptorType;
    this._descriptorId = descriptor.descriptorId;
  }

}
