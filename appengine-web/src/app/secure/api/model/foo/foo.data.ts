import { ModelKey } from '@gae-web/appengine-utility';
import { AbstractDescribedDatabaseModelSerializer, DescribedDatabaseModelData } from '@gae-web/appengine-api';
import { Foo } from './foo';

// MARK: DTO
export class FooData extends DescribedDatabaseModelData {

  public static fromJson(json: any): FooData {
    const data = new FooData();
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

// MARK: Serializer
export class FooSerializer
  extends AbstractDescribedDatabaseModelSerializer<Foo, FooData> {

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
