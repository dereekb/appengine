package com.dereekb.gae.server.api.firebase;

import com.google.firebase.FirebaseApp;

/**
 * Accessor for a configured {@link FirebaseApp} instance.
 *
 * @author dereekb
 *
 */
public interface FirebaseService {

	/**
	 * Returns the initialized firebase app.
	 *
	 * @return {@link FirebaseApp}. Never {@code null}.
	 * @throws RuntimeException
	 *             thrown if the app could not be initialized or is unavailable.
	 */
	public FirebaseApp getFirebaseApp() throws RuntimeException;

}
