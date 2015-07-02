package com.dereekb.gae.utilities.function.staged.components;

import com.dereekb.gae.utilities.function.staged.StagedFunction;

/**
 * Represents stages in a {@link StagedFunction} function call lifecycle.
 * 
 * @author dereekb
 */
public enum StagedFunctionStage {

	/**
	 * The function handler was interrupted.
	 */
	INTERRUPTED(-1),

	/**
	 * The function handler has been initialized, and never run.
	 */
	INITIALIZED(0),

	/**
	 * The function handler has started.
	 */
	STARTED(1),

	/**
	 * The function is about to start running.
	 */
	FUNCTION_STARTED(2),

	/**
	 * The function is running,
	 */
	FUNCTION_RUNNING(3),

	/**
	 * The function has finished running, and is about to save.
	 */
	FUNCTION_FINISHED(4),

	/**
	 * The function handler is about to save.
	 * 
	 * This is not called if the function handler does not save.
	 */
	PRE_SAVING(5),

	/**
	 * The function handler is saving.
	 * 
	 * This is not called if the function handler does not save.
	 */
	SAVING(6),

	/**
	 * The function handler has finished saving.
	 * 
	 * This is not called if the function handler does not save.
	 */
	POST_SAVING(7),

	/**
	 * The function handler is done running, and is about to terminate.
	 */
	FINISHED(8),

	/**
	 * The function handler has completed successfully.
	 * At this point, the function handler has been cleared, and is ready to be used again.
	 */
	COMPLETED(9);

	private final int stage;

	private StagedFunctionStage(int stage) {
		this.stage = stage;
	}

	public boolean before(StagedFunctionStage stage) {
		return (this.stage < stage.stage);
	}

	public boolean after(StagedFunctionStage stage) {
		return (this.stage > stage.stage);
	}

}
