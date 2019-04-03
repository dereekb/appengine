
/**
 * State service declaration used by the GAE Gateway module for performing segues to the app.
 */
export abstract class GatewaySegueService {

  abstract segueToGateway();

  abstract segueToOnboarding();

  abstract segueToApp();

}
