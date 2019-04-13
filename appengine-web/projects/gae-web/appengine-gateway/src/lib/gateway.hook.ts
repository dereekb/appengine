import { TransitionService, TransitionHookFn, Transition, HookResult, StateService } from '@uirouter/core';
import { of } from 'rxjs';
import { UserLoginTokenService } from '@gae-web/appengine-token';
import { catchError, timeout, map } from 'rxjs/operators';

const DEFAULT_SIGN_IN_STATE = 'signin';

/**
 * This file contains a Transition Hook which protects a
 * route that requires authentication.
 *
 * This hook redirects to the signin state when both:
 * - The user is not authenticated
 * - The user is navigating to a state that requires authentication
 */
export function secureGatewayHook(transitionService: TransitionService, signInState = DEFAULT_SIGN_IN_STATE) {

  // Matches if the destination state's data property has a truthy 'isSecure' property
  const isSecureCriteria = {
    to: (state) => state.data && state.data.isSecure
  };

  // Function that returns a redirect for the current transition to the login state
  // if the user is not currently authenticated (according to the AuthService)

  // https://ui-router.github.io/ng2/docs/latest/modules/transition.html#hookresult
  const redirectToLogin: TransitionHookFn = (transition: Transition): HookResult => {
    const tokenService: UserLoginTokenService = transition.injector().get(UserLoginTokenService);
    const $state: StateService = transition.router.stateService;

    const catchFn = (at: string) => {
      return (error) => {
        console.log('auth.hook Auth failed: ' + at + ' > ' + error);
        return of(false);
      };
    };

    // Take at max 5000ms.
    return tokenService.isAuthenticated()
      .pipe(
        catchError(catchFn('error')),
        timeout(5000),
        catchError(catchFn('timeout')),
        map((isAuthenticated: boolean): HookResult => {
          if (isAuthenticated) {
            return true;  // Continue on.
          } else {
            return $state.target(signInState, undefined, { location: true }); // Redirect
          }
        })
      ).toPromise() as HookResult;
  };

  // Register the "requires auth" hook with the TransitionsService
  transitionService.onBefore(isSecureCriteria, redirectToLogin, { priority: 100 });
}
