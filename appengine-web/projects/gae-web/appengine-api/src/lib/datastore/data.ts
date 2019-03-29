import { ISO8601DateString } from '@gae-web/appengine-utility';
import { DataConverterUtility } from '@gae-web/appengine-utility';
import { ModelKey, ModelUtility, MutableUniqueModel } from '@gae-web/appengine-utility';
import { OwnerId, SearchId, AbstractSearchableDatabaseModel, AbstractOwnedDatabaseModel } from './model';
import { DateTime } from 'luxon';
import { AbstractClientModelSerializer } from '../model/client';

export abstract class DatedModelData {

    public date?: ISO8601DateString;

    public getDateValue(): DateTime | undefined {
        return DataConverterUtility.dateFromString(this.date);
    }

    public setDateValue(date: DateTime | undefined): void {
        this.date = DataConverterUtility.dateToString(date);
    }

}

export abstract class DatabaseModelData extends DatedModelData {

    public key?: ModelKey;

    constructor(key?: ModelKey) {
        super();
        this.key = key;
    }

    public setKey(key: ModelKey | undefined) {
        this.key = ModelUtility.modelKeyToString(key);
    }

}

export abstract class AbstractDatabaseModelSerializer<T extends MutableUniqueModel, O extends DatabaseModelData>
    extends AbstractClientModelSerializer<T, O> {

    public convertToDto(model: T): O {
        const data = this.makeDto();

        data.setKey(model.modelKey);

        return data;
    }

    public convertToModel(dto: O): T {
        const model = this.makeModel();

        if (dto.key) {
            model.modelKey = dto.key;
        }

        return model;
    }

    protected abstract makeModel(): T;

    protected abstract makeDto(): O;

}

// MARK: Owned
export abstract class OwnedDatabaseModelData extends DatabaseModelData {
    public ownerId?: OwnerId;
}

export abstract class AbstractOwnedDatabaseModelSerializer<T extends AbstractOwnedDatabaseModel, O extends OwnedDatabaseModelData>
    extends AbstractDatabaseModelSerializer<T, O> {

    public convertToDto(model: T): O {
        const data = super.convertToDto(model);

        data.ownerId = model.ownerId;

        return data;
    }

    public convertToModel(dto: O): T {
        const model = super.convertToModel(dto);

        if (dto.ownerId) {
            model.ownerId = dto.ownerId;
        }

        return model;
    }

}

// MARK: Searchable
export abstract class SearchableDatabaseModelData extends OwnedDatabaseModelData {
    public searchId?: SearchId;
}

export abstract class AbstractSearchableDatabaseModelSerializer<T extends AbstractSearchableDatabaseModel, O extends SearchableDatabaseModelData>
    extends AbstractOwnedDatabaseModelSerializer<T, O> {

    public convertToDto(model: T): O {
        const data = super.convertToDto(model);

        data.searchId = model.searchId;

        return data;
    }

    public convertToModel(dto: O): T {
        const model = super.convertToModel(dto);

        if (dto.searchId) {
            dto.searchId = model.searchId;
        }

        return model;
    }

}
