package com.dereekb.gae.extras.gen.utility.spring.bean.impl;

import com.dereekb.gae.extras.gen.utility.spring.bean.SpringBeansXMLEntity;
import com.jamesmurty.utils.XMLBuilder2;

public class SpringBeansXMLEntityImpl
        implements SpringBeansXMLEntity {

	private final XMLBuilder2 builder;

	public SpringBeansXMLEntityImpl(XMLBuilder2 builder) {
		this.builder = builder;
	}

	public XMLBuilder2 getBuilder() {
		return this.builder;
	}

}
