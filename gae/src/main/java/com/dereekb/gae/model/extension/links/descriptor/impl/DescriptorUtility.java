package com.dereekb.gae.model.extension.links.descriptor.impl;

import com.googlecode.objectify.Key;


public class DescriptorUtility {

	public static <T> DescriptorImpl withKeyId(Key<T> key) throws NullPointerException {
		String type = key.getKind();
		return withKeyId(type, key);
	}

	public static <T> DescriptorImpl withKeyId(String descriptorType,
	                                           Key<T> key) throws NullPointerException {
		Long id = key.getId();
		String idString = id.toString();
		return new DescriptorImpl(descriptorType, idString);
	}

}
