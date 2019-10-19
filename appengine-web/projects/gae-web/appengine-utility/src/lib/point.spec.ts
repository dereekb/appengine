import 'jasmine-expect';
import { Point } from './point';
import { DataConverterUtility } from './data';

describe('Point', () => {

  const latitude = 35;
  const longitude = 35;

  let point: Point;

  beforeEach(() => {
    point = new Point({ latitude, longitude });
  });

  it('should set lat/long from an object.', () => {
    expect(point.latitude).toBe(latitude);
    expect(point.longitude).toBe(longitude);
  });

  it('should convert to json using DataConverterUtility', () => {
    const json = DataConverterUtility.asJson(point);

    expect(json.latitude).toBe(latitude);
    expect(json.longitude).toBe(longitude);
  });

});
