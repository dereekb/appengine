package com.dereekb.gae.test.model.extension.link.model;

import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractModelGenerator;
import com.dereekb.gae.model.extension.generation.impl.keys.StringModelKeyGenerator;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;


public class TestLinkModelBGenerator extends AbstractModelGenerator<TestLinkModelB> {

	public TestLinkModelBGenerator() {
		super(TestLinkModelB.class, StringModelKeyGenerator.GENERATOR);
	}

	@Override
	protected TestLinkModelB generateModel(ModelKey key,
	                                       GeneratorArg arg) {
		
		TestLinkModelB a = new TestLinkModelB();
		
		a.setModelKey(key);
		
		return a;
	}
	
}