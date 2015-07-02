package com.dereekb.gae.server.taskqueue.system;


public enum TaskRequestTimingType {

	/**
	 * Number of milliseconds until the request.
	 */
	COUNTDOWN,

	/**
	 * {@link Date} as a long to start the request.
	 */
	ETA;

}
