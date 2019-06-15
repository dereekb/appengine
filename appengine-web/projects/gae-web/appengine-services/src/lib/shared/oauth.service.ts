import { Observable } from 'rxjs';

export interface OAuthLoginServiceTokenResponse {
  service: string;  // OAuth Service Name
  token: OAuthLoginServiceAccessToken;
}

export interface OAuthLoginServiceAccessToken {
  accessToken: string;
  // TODO: Add expires in, etc.
}

export abstract class OAuthLoginService {

  constructor(public readonly serviceName: string) { }

  // MARK: OAuthSignInService
  abstract tokenLogin(): Observable<OAuthLoginServiceTokenResponse>;

}
