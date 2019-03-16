package com.dereekb.gae.server.auth.deprecated.permissions.evaluator;

/**
 * Denotes a permissions handler, which is able to handles a small range of permissions.
 * 
 * For example, a Permissions handler that has a response key value of 'place', then 
 * we know that this handler is capable of determining permissions related to place.
 * 
 * @author dereekb
 */
public interface PermissionsHandler {

	/**
	 * The keyword this handler will respond that is used as a key for determining which handler to use.
	 * 
	 * For example, if this handler is going to respond to "place.'...'", then the keyword should be "place".
	 *
	 * @return String keyword this handler responds to.
	 */
	public String getResponseKey(); 
	
	/**
	 * Returns true if the event passes permission. 
	 * 
	 * When this object is used by the Permissions Evaluator, 
	 * this function is called by default if no other function annotated with the function in the event is defined.
	 * 
	 * @param event The permission event to evaluate.
	 * @return True if we are going to grant permission.
	 */
	public boolean hasPermission(PermissionsEvent event);

	/**
	 * Returns a prefix to append to a given permission request.
	 * 
	 * Example: If the prefix is 'visit.model.location', and the request is 'place.edit',
	 * then using the prefix the full permission 'visit.model.location.place.edit' is created.
	 * 
	 * @param request
	 * @return
	 */
	public String getPermissionStringPrefix();
	
}
