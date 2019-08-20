import { JsonConvertable } from './data';
import { AssertMax, AssertMin } from './assert';

// MARK: General
/**
 * Represents a point in the world with Latitude and Longitude.
 */
export class Point implements JsonConvertable {

  public static MIN_LATITUDE = -90.0;
  public static MAX_LATITUDE = 90.0;
  public static MIN_LONGITUDE = -180.0;
  public static MAX_LONGITUDE = 180.0;

  private _latitude = 0;
  private _longitude = 0;

  public static fromJson(json: any): Point {
    return new Point(json);
  }

  constructor(point?: { latitude: number, longitude: number }) {
    if (point) {
      this.latitude = point.latitude;
      this.longitude = point.longitude;
    }
  }

  public static makePoint(latitude: number, longitude: number): Point {
    return new Point({ latitude, longitude });
  }

  get latitude(): number {
    return this._latitude;
  }

  @AssertMin(Point.MIN_LATITUDE)
  @AssertMax(Point.MAX_LATITUDE)
  set latitude(latitude: number) {
    this._latitude = latitude;
  }

  get longitude(): number {
    return this._longitude;
  }

  @AssertMin(Point.MIN_LONGITUDE)
  @AssertMax(Point.MAX_LONGITUDE)
  set longitude(longitude: number) {
    this._longitude = longitude;
  }

  // MARK: JSON
  public toJSON(): any {
    return {
      latitude: this.latitude,
      longitude: this.longitude
    };
  }

}
