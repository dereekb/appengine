package com.dereekb.gae.test.model.extension.link.model;

import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractModelGenerator;
import com.dereekb.gae.model.extension.generation.impl.keys.LongModelKeyGenerator;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;


public class TestLinkModelAGenerator extends AbstractModelGenerator<TestLinkModelA> {

	public TestLinkModelAGenerator() {
		super(TestLinkModelA.class, LongModelKeyGenerator.GENERATOR);
	}

	@Override
	protected TestLinkModelA generateModel(ModelKey key,
	                                       GeneratorArg arg) {
		
		TestLinkModelA a = new TestLinkModelA();
		
		a.setModelKey(key);
		
		return a;
	}
	
}