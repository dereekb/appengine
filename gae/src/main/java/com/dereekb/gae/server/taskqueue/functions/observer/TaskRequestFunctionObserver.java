package com.dereekb.gae.server.taskqueue.functions.observer;

import java.util.List;

import com.dereekb.gae.server.taskqueue.builder.TaskRequestSender;
import com.dereekb.gae.server.taskqueue.exception.SubmitTaskException;
import com.dereekb.gae.utilities.function.staged.StagedFunction;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;
import com.dereekb.gae.utilities.function.staged.observer.StagedFunctionObserver;

/**
 * {@link StagedFunctionObserver} that runs tasks using a
 * {@link TaskRequestSender}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class TaskRequestFunctionObserver<T>
        implements StagedFunctionObserver<T> {

	private TaskRequestSender<T> sender;

	public TaskRequestFunctionObserver(TaskRequestSender<T> sender) {
		this.sender = sender;
	}

	public TaskRequestSender<T> getSender() {
		return this.sender;
	}

	public void setSender(TaskRequestSender<T> sender) {
		this.sender = sender;
	}

	@Override
	public void functionHandlerCallback(StagedFunctionStage stage,
	                                    StagedFunction<T, ?> handler) {
		List<T> objects = handler.getFunctionObjects();

		try {
			this.sender.sendTasks(objects);
		} catch (SubmitTaskException e) {
			// TODO: Catch if it fails.
		}
	}

}
