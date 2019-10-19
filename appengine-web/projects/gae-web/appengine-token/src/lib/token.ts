import { JwtHelperService } from '@auth0/angular-jwt';
import { NumberModelKey, DateTimeUtility } from '@gae-web/appengine-utility';
import { StringModelKey } from '@gae-web/appengine-utility';
import { HttpResponse } from '@angular/common/http';
import { DateTime } from 'luxon';

export type LoginId = NumberModelKey;
export type LoginPointerId = StringModelKey;

// TODO: Change EncodedToken to an interface, and this value to EncodedTokenString so DecodedToken can implement this interface and it maps the server better.

export type EncodedToken = StringModelKey;
export type EncodedRefreshToken = EncodedToken;

export enum TokenType {

  /**
   * Token without a login attached to it, implying registration is required.
   */
  Registration = 0,

  /**
   * Token used for refreshing.
   */
  Refresh = 1,

  /**
   * Full auth token.
   */
  Full = 2

}

export enum LoginPointerType {

  None = -1,

  System = 0,

  Anonymous,

  ApiKey,

  RefreshToken,

  Password,

  OAuthGoogle,

  OAuthFacebook

}

const LOGIN_KEY = 'lgn';
const LOGIN_POINTER_KEY = 'ptr';
const LOGIN_POINTER_TYPE_KEY = 'pt';

export type LoginToken = DecodedFullToken | DecodedRegistrationToken | DecodedRefreshToken;

/**
 * Shared LoginToken utility for building LoginTokens from an EncodedToken.
 */
export class LoginTokenUtility {

  public static readonly JWT_HELPER: JwtHelperService = new JwtHelperService();

  /**
   * Creates a new LoginToken from the input encoded token.
   */
  static fromEncodedToken(encoded: EncodedToken): LoginToken {
    const raw: any = this.JWT_HELPER.decodeToken(encoded);
    const typeId: LoginPointerType = raw[LOGIN_POINTER_TYPE_KEY];
    const pointerid: LoginPointerId = raw[LOGIN_POINTER_KEY];
    let loginId: LoginId | undefined = raw[LOGIN_KEY];
    let loginToken: LoginToken;

    switch (typeId) {
      case LoginPointerType.RefreshToken:
        loginId = loginId || 0;
        loginToken = new DecodedRefreshToken(loginId, encoded, pointerid, raw);
        break;
      default:
        if (!loginId) {
          loginToken = new DecodedRegistrationToken(encoded, pointerid, raw);
        } else {
          loginToken = new DecodedFullToken(loginId, encoded, pointerid, raw);
        }
    }

    return loginToken;
  }

}

/**
 * Abstract decoded token.
 */
export abstract class DecodedLoginToken {

  private _pair: LoginTokenPair;
  private _pointerType: LoginPointerType;

  constructor(private _encoded: EncodedToken, private _type: TokenType, private _pointer: LoginPointerId, private _raw: any) {
    let typeId: number = this._raw[LOGIN_POINTER_TYPE_KEY];

    if (!typeId && typeId !== 0) {
      typeId = -1;
    }

    this._pointerType = typeId;
  }

  get encodedToken() {
    return this._encoded;
  }

  get pointerType() {
    return this._pointerType;
  }

  get pointer() {
    return this._pointer;
  }

  get type() {
    return this._type;
  }

  get raw() {
    return this._raw;
  }

  get decodedPayload() {
    return this._raw;
  }

  get expiration(): DateTime {
    const date: Date = LoginTokenUtility.JWT_HELPER.getTokenExpirationDate(this._encoded);
    return DateTimeUtility.dateTimeFromInput(date);
  }

  get isExpired(): boolean {
    return this.expiration < DateTime.local();
  }

  get login(): LoginId | undefined {
    return undefined;
  }

  public asLoginTokenPair() {
    if (!this._pair) {
      this._pair = new LoginTokenPair(this._encoded, this._pointer);
    }

    return this._pair;
  }

  public setLoginTokenPair(pair) {
    this._pair = pair;
  }

}

export class DecodedRegistrationToken extends DecodedLoginToken {

  constructor(encoded: EncodedToken, pointer: LoginPointerId, raw: any) {
    super(encoded, TokenType.Registration, pointer, raw);
  }

}

export abstract class DecodedLoginIncludedToken extends DecodedLoginToken {

  constructor(private _login: LoginId, encoded: EncodedToken, type: TokenType, pointer: LoginPointerId, raw: any) {
    super(encoded, type, pointer, raw);
  }

  get login(): LoginId {
    return this._login;
  }

}

export class DecodedFullToken extends DecodedLoginIncludedToken {

  constructor(login: LoginId, encoded: EncodedToken, pointer: LoginPointerId, raw: any) {
    super(login, encoded, TokenType.Full, pointer, raw);
  }

}

export class DecodedRefreshToken extends DecodedLoginIncludedToken {

  constructor(login: LoginId, encoded: EncodedToken, pointer: LoginPointerId, raw: any) {
    super(login, encoded, TokenType.Refresh, pointer, raw);
  }

}

export interface LoginTokenPairJson {
  token: EncodedToken;
  pointer?: LoginPointerId;
}

/**
 * Appengine login token response.
 */
export class LoginTokenPair {

  private _token: EncodedToken;
  private _pointer: LoginPointerId;

  private _decoded: LoginToken;

  public static fromResponse(res: HttpResponse<LoginTokenPairJson>): LoginTokenPair {
    return LoginTokenPair.fromJson(res.body);
  }

  public static fromJson(json: LoginTokenPairJson): LoginTokenPair {
    const token: EncodedToken = json.token;
    const pointer: LoginPointerId = json.pointer;
    return new LoginTokenPair(token, pointer);
  }

  constructor(token: EncodedToken, pointer?: LoginPointerId) {
    this._token = token;

    if (pointer) {
      this._pointer = pointer;
    }
  }

  get token(): EncodedToken {
    return this._token;
  }

  get pointer(): LoginPointerId {
    return this._pointer || this.decode().pointer;
  }

  public decode(): LoginToken {
    if (!this._decoded) {
      this._decoded = LoginTokenUtility.fromEncodedToken(this._token);
      this._decoded.setLoginTokenPair(this);
    }

    return this._decoded;
  }

  get isExpired(): boolean {
    return this.decode().isExpired;
  }

}
