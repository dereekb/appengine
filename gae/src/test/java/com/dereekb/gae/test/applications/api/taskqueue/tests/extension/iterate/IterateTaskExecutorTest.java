package com.dereekb.gae.test.applications.api.taskqueue.tests.extension.iterate;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.model.extension.iterate.exception.IterationLimitReachedException;
import com.dereekb.gae.model.extension.iterate.impl.IterateTaskExecutorFactoryImpl;
import com.dereekb.gae.model.extension.iterate.impl.IterateTaskExecutorImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.web.taskqueue.controller.extension.iterate.impl.IterateTaskInputImpl;

/**
 * Tests {@link IterateTaskExecutorImpl} and
 * {@link IterateTaskExecutorFactoryImpl}.
 *
 * @author dereekb
 *
 */
public abstract class IterateTaskExecutorTest<T extends ObjectifyModel<T>> extends ApiApplicationTestContext {

	protected TestModelGenerator<T> modelGenerator;
	private IterateTaskExecutorFactoryImpl<T> factory;

	public void setModelGenerator(TestModelGenerator<T> generator) {
    	this.modelGenerator = generator;
    }

	public void setFactory(IterateTaskExecutorFactoryImpl<T> factory) {
    	this.factory = factory;
    }

	@Test
	public void testExecutorFactory() {
		TestTask task = new TestTask();
		Integer limit = 10;
		Integer total = limit + 1;

		IterateTaskExecutorImpl<T> executor = this.factory.makeExecutor(task);
		executor.setIteratorLimit(limit);
		this.modelGenerator.generate(total);

		// Generated will be available in the query.
		try {
			IterateTaskInputImpl input = new IterateTaskInputImpl(null, null, null, null);
			executor.executeTask(input);

			Assert.fail("Should not have iterated more than the limit.");

		} catch (IterationLimitReachedException e) {
			Assert.assertTrue(task.getCount() == limit);
			Assert.assertNotNull(e.getCursor());

			// Execute again.
			IterateTaskInputImpl input = new IterateTaskInputImpl(null, null, e.getCursor(), 1, null);

			try {
				executor.executeTask(input);
				Assert.assertTrue(task.getCount() == total);
			} catch (Exception f) {
				Assert.fail("Should have started iteration from cursor.");
			}
		}

	}

	private class TestTask
	        implements Task<ModelKeyListAccessor<T>> {

		private Integer count = 0;

		public Integer getCount() {
			return this.count;
		}

		@Override
		public void doTask(ModelKeyListAccessor<T> input) throws FailedTaskException {
			List<ModelKey> keys = input.getModelKeys();
			this.count += keys.size();
		}

	}


}
