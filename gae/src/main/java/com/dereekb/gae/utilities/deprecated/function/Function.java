package com.dereekb.gae.utilities.function;

import java.util.Collection;

import com.dereekb.gae.utilities.deprecated.function.staged.exception.StagedFunctionRunningException;

public interface Function<W> {

	/**
	 * Runs the function.
	 * 
	 * @return Returns true if the function completed without errors.
	 */
	public boolean run();

	/**
	 * Whether or not the function is currently running.
	 * 
	 * @return
	 */
	public boolean isRunning();

	/**
	 * Clears all objects from the function.
	 * 
	 * @throws StagedFunctionRunningException
	 *             Thrown if the function is currently running.
	 */
	public void clearObjects() throws StagedFunctionRunningException;

	/**
	 * Adds an object to the function.
	 * 
	 * @param object
	 * @throws StagedFunctionRunningException
	 *             Thrown if the function is currently running.
	 */
	public void addObject(W object) throws StagedFunctionRunningException;

	/**
	 * Adds a set of objects to the function.
	 * 
	 * @param objects
	 * @throws StagedFunctionRunningException
	 *             Thrown if the function is currently running.
	 */
	public void addObjects(Collection<W> objects) throws StagedFunctionRunningException;

}
