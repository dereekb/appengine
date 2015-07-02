package com.dereekb.gae.test.runnable;

import java.util.List;

public class RunnableTestContainer<T extends RunnableTest>
        implements RunnableTest {

	protected List<T> tests;

	public RunnableTestContainer() {}

	public RunnableTestContainer(List<T> tests) {
		this.tests = tests;
	}

	public List<T> getTests() {
		return this.tests;
	}

	public void setTests(List<T> tests) {
		this.tests = tests;
	}

	// RunnableTest
	@Override
	public void runTests() {

		if (this.tests == null || this.tests.isEmpty()) {
			throw new RuntimeException("No tests specified.");
		}

		for (T test : this.tests) {
			test.runTests();
		}
	}

}
