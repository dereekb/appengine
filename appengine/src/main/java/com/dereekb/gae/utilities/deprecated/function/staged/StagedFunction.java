package com.dereekb.gae.utilities.function.staged;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.utilities.deprecated.function.SavableFunction;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.deprecated.function.staged.delegates.StagedFunctionDelegate;
import com.dereekb.gae.utilities.deprecated.function.staged.delegates.StagedFunctionSaveDelegate;
import com.dereekb.gae.utilities.deprecated.function.staged.exception.StagedFunctionAccessDeniedException;
import com.dereekb.gae.utilities.deprecated.function.staged.exception.StagedFunctionNoObjectsException;
import com.dereekb.gae.utilities.deprecated.function.staged.exception.StagedFunctionRunningException;
import com.dereekb.gae.utilities.deprecated.function.staged.filter.FilteredStagedFunction;
import com.dereekb.gae.utilities.deprecated.function.staged.observer.StagedFunctionObserverMap;

/**
 * A functions handler carries out a function on a set of objects, then saves
 * the results. A function handler does not return anything, but modifies the
 * wrapped {@link StagedFunctionObject} objects instead.
 *
 * Uses an {@link StagedFunctionObserverMap} to implement Observer-type
 * functionality. The observer map is notified of each
 * {@link StagedFunctionStage}, and notified each observer for that stage. Uses
 * an optional {@link StagedFunctionSaveDelegate} to save changes.
 *
 * @author dereekb
 * @param <T>
 *            Type of the base object used in this function.
 * @param <W>
 *            Functional Wrapper of the main object that extends
 *            {@link StagedFunctionObject}.
 * @see {@link FilteredStagedFunction} for adding additional observers that can
 *      filter out objects at various stages.
 */
public abstract class StagedFunction<T, W extends StagedFunctionObject<T>>
        implements SavableFunction<T, W> {

	private static final boolean DEFAULT_SHOULD_CLEAR_ON_COMPLETION = true;

	private StagedFunctionStage currentStage = StagedFunctionStage.INITIALIZED;

	private boolean allowAccess = true;
	private boolean saveSuccess = false;

	/**
	 * Map of observers who can watch changes or elements during stanges.
	 */
	private StagedFunctionObserverMap<T, W> observers;

	/**
	 * (Optional) Type used to save changes.
	 */
	private StagedFunctionSaveDelegate<T> saveDelegate;

	/**
	 * Is unused.
	 */
	@Deprecated
	private StagedFunctionDelegate<T> delegate;

	/**
	 * All objects set for this function. This list is immutable and used for
	 * reference.
	 */
	private final List<W> objects = new ArrayList<W>();

	/**
	 * All current objects. This list is mutable by extensions.
	 */
	private List<W> workingObjects = new ArrayList<W>();

	public StagedFunction() {
		this(null, null);
	}

	public StagedFunction(StagedFunctionSaveDelegate<T> saveDelegate) {
		this(saveDelegate, null);
	}

	public StagedFunction(StagedFunctionSaveDelegate<T> saveDelegate, StagedFunctionDelegate<T> delegate) {
		this.saveDelegate = saveDelegate;
		this.delegate = delegate;
		this.observers = new StagedFunctionObserverMap<T, W>();
	}

	@Override
    public final void clearObjects() throws StagedFunctionRunningException {
		if (this.isRunning() == false) {
			this.objects.clear();
		} else {
			throw new StagedFunctionRunningException();
		}
	}

	@Override
    public final void addObject(W object) throws StagedFunctionRunningException {
		if (this.isRunning() == false) {
			this.objects.add(object);
		} else {
			throw new StagedFunctionRunningException();
		}
	}

	@Override
    public final void addObjects(Collection<W> objects) throws StagedFunctionRunningException {
		if (this.isRunning() == false) {
			this.objects.addAll(objects);
		} else {
			throw new StagedFunctionRunningException();
		}
	}

	public void initializeWorkingObjects() throws StagedFunctionNoObjectsException {
		List<W> workingObjects = new ArrayList<W>(this.objects);
		this.setWorkingObjects(workingObjects);
	}

	/**
	 * Runs the function.
	 *
	 * If not objects are given, the function will instantly be marked as completed and return.
	 *
	 * Any exceptions thrown will first be caught, then
	 *
	 * @return Returns true if it completes the function. Encountering exceptions will automatically cause success to
	 *         return false.
	 */
	@Override
    public final boolean run() {

		boolean success = false;

		if (this.isRunning()) {
			throw new StagedFunctionRunningException();
		} else {
			try {
				this.started();
				this.startFunction();
				this.finishFunction();
				this.saveFunctionChanges();
				this.completed();

				success = true;
			} catch (StagedFunctionNoObjectsException e) {
				this.failedWithNoObjects();
			} catch (Exception e) {
				boolean rethrow = this.functionInterrupted(e);
				if (rethrow) {
					throw e;
				}
			}
		}

		return success;
	}

	/**
	 * Thrown when an exception occurs. Sets the stage as interrupted and allows access to working objects.
	 *
	 * Recovery should not be attempted from this point by the StagedFunction.
	 *
	 * Lets the run() function re-throw the exception by default.
	 *
	 * @param e
	 * @return True if we want the run() function to re-throw the exception.
	 */
	protected boolean functionInterrupted(Exception e) {
		this.setAllowAccess(true);
		this.setCurrentStage(StagedFunctionStage.INTERRUPTED);
		this.cleanup();
		return true;
	}

	protected void notifyObserversOfStage(StagedFunctionStage stage) {
		this.setAllowAccess(true);
		this.observers.notifyObserversOfStage(stage, this);
		this.setAllowAccess(false);
	}

	/**
	 * First function to be called. Sets up the working objects and function.
	 */
	protected void started() {
		this.setAllowAccess(false);
		this.initializeWorkingObjects();
		this.setupFunction();
		this.setCurrentStage(StagedFunctionStage.STARTED);
	}

	/**
	 * Called when the function should start.
	 *
	 * Notifies observers and calls doFunction().
	 */
	protected void startFunction() {
		this.setCurrentStage(StagedFunctionStage.FUNCTION_STARTED);
		this.doFunction();
	}

	/**
	 * Called once the function has been completed.
	 */
	protected void finishFunction() {
		this.setCurrentStage(StagedFunctionStage.FUNCTION_FINISHED);
	}

	/**
	 * Called after the function has been completed, and attempts to save changes using the save delegate.
	 */
	protected void saveFunctionChanges() {
		if (this.saveDelegate != null) {
			this.setCurrentStage(StagedFunctionStage.PRE_SAVING);

			this.currentStage = StagedFunctionStage.SAVING;
			List<T> savableObjects = this.getFunctionObjects(StagedFunctionStage.SAVING);
			boolean saveSuccess = this.saveDelegate.saveFunctionChanges(savableObjects);
			this.setSaveSuccess(saveSuccess);

			this.setCurrentStage(StagedFunctionStage.POST_SAVING);
		}
	}

	/**
	 * Called after the function has finished saving or attempted saving.
	 */
	protected void completed() {
		this.setCurrentStage(StagedFunctionStage.FINISHED);
		this.cleanup();
		this.currentStage = StagedFunctionStage.COMPLETED;
	}

	/**
	 * Called when the function encounters a {@link StagedFunctionNoObjectsException}.
	 *
	 * It cleans up and sets the stage to completed.
	 */
	protected void failedWithNoObjects() {
		this.cleanup();
		this.currentStage = StagedFunctionStage.COMPLETED;
	}

	protected void cleanup() {
		boolean shouldClearObjects = DEFAULT_SHOULD_CLEAR_ON_COMPLETION;

		if (this.delegate != null) {
			shouldClearObjects = this.delegate.handlerShouldClearOnCompletion(this);
		}

		if (shouldClearObjects) {
			this.objects.clear();
		}
	}

	/**
	 * Setup performed before the function begins.
	 */
	protected void setupFunction() {

	};

	/**
	 * Begins the function on the target objects.
	 */
	protected abstract void doFunction();

	protected final Collection<W> getWorkingObjects() {
		Collection<W> collection = this.workingObjects;
		return collection;
	}

	public final List<T> getFunctionObjects() {
		return this.getFunctionObjects(this.currentStage);
	}

	protected List<T> getFunctionObjects(StagedFunctionStage stage) {
		Collection<W> workingObjects = this.getWorkingObjects();
		return getFunctionObjects(workingObjects, stage);
	}

	/**
	 * Returns the Objects Iterator for iterating over all input objects. Is allowed only when the function is not running.
	 *
	 * Use getWorkingObjectsIterator() function to retrieve current objects.
	 *
	 * @return
	 * @throws StagedFunctionAccessDeniedException
	 */
	public final Iterable<W> getObjectsIterator() throws StagedFunctionAccessDeniedException {
		if (this.isRunning() == false) {
			throw new StagedFunctionAccessDeniedException();
		}

		List<W> objectsCopy = new ArrayList<W>(this.objects);
		return objectsCopy;
	}

	/**
	 * Returns the Objects Iterator for the Working Objects. Is allowed only when notifying observers and the function
	 * is not running.
	 *
	 * @return
	 * @throws StagedFunctionAccessDeniedException
	 */
	public final Iterable<W> getWorkingObjectsIterator() throws StagedFunctionAccessDeniedException {

		if (this.isAllowAccess() == false) {
			throw new StagedFunctionAccessDeniedException();
		}

		List<W> collectionCopy = new ArrayList<W>(this.getWorkingObjects());
		return collectionCopy;
	}

	public final int getObjectsCount() {
		return this.objects.size();
	}

	public final int getWorkingObjectsCount() {
		return this.workingObjects.size();
	}

	/**
	 * Sets the working objects array to the provided value.
	 *
	 * @param objects
	 * @throws StagedFunctionNoObjectsException
	 *             Thrown if no objects are given.
	 */
	protected final void setWorkingObjects(List<W> objects) throws StagedFunctionNoObjectsException {
		this.workingObjects = objects;

		if (this.workingObjects == null || this.workingObjects.isEmpty()) {
			throw new StagedFunctionNoObjectsException();
		}
	}

	protected void setCurrentStage(StagedFunctionStage stage) {
		this.currentStage = stage;
		this.notifyObserversOfStage(stage);
	}

	public final StagedFunctionObserverMap<T, W> getObservers() {
		return this.observers;
	}

	public final void setObservers(StagedFunctionObserverMap<T, W> observers) {
		if (this.isRunning()) {
			throw new StagedFunctionRunningException();
		} else {
			if (observers == null) {
				throw new NullPointerException("Cannot set observers to null.");
			}

			this.observers = observers;
		}
	}

	/**
	 * Returns the function models from the given function handler objects.
	 */
	public static <T, W extends StagedFunctionObject<T>> List<T> getFunctionObjects(Iterable<W> objects,
	                                                                                 StagedFunctionStage stage) {
		List<T> models = new ArrayList<T>();

		for (W object : objects) {
			T model = object.getFunctionObject(stage);
			models.add(model);
		}

		return models;
	}

	@Override
    public final void setSaveDelegate(StagedFunctionSaveDelegate<T> saveDelegate)
	        throws StagedFunctionRunningException {
		if (this.isRunning()) {
			throw new StagedFunctionRunningException();
		} else {
			this.saveDelegate = saveDelegate;
		}
	}

	@Deprecated
	public final void setDelegate(StagedFunctionDelegate<T> delegate) throws StagedFunctionRunningException {
		if (this.isStopped()) {
			this.delegate = delegate;
		} else {
			throw new StagedFunctionRunningException();
		}
	}

	public final boolean isStopped() {
		boolean isStopped = false;

		switch (this.currentStage) {
			case COMPLETED:
			case INITIALIZED:
			case INTERRUPTED: {
				isStopped = true;
			}
				break;
			default:
				break;
		}

		return isStopped;
	}

	@Override
    public final boolean isRunning() {
		return (this.isStopped() == false);
	}

	@Override
    public final boolean savedSuccessfully() {
		return this.saveSuccess;
	}

	protected final void setSaveSuccess(boolean saveSuccess) {
		this.saveSuccess = saveSuccess;
	}

	public final StagedFunctionStage getCurrentStage() {
		return this.currentStage;
	}

	public boolean isAllowAccess() {
		boolean accessAllowed = (this.allowAccess || (this.isStopped()));
		return accessAllowed;
	}

	private final void setAllowAccess(boolean allowAccess) {
		this.allowAccess = allowAccess;
	}

}
