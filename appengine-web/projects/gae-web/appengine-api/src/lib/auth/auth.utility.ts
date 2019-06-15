import { EncodedToken } from '@gae-web/appengine-token';

export class AuthUtility {

  static buildHeaderWithAuthentication(token: EncodedToken) {
    const headers = {
      Authorization: `Bearer ${token}`
    };

    return headers;
  }

}
