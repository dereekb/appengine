package com.thevisitcompany.gae.deprecated.model.users.user;

/**
 * Delegate used for adding or removing items from an user using an observer.
 * 
 * @author dereekb
 * 
 * @param <T>
 *            Item Type
 */
public interface UserItemChangeDelegate<T> {

	/**
	 * Links the given item to the user.
	 * 
	 * @param user
	 * @param item
	 * @return True if the item change was successful.
	 */
	public boolean changeUserItem(User user,
	                              UserItemChange change,
	                              T item);

}
