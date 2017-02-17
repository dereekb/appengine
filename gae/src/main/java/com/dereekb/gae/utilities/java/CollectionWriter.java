package com.dereekb.gae.utilities.java;

import java.util.Collection;
import java.util.Iterator;

/**
 * Utility for helping write out a collection of objects.
 * @author dereekb
 *
 */
public class CollectionWriter {

	private static String DEFAULT_SEPARATOR = ", ";
	
	private final String separator;

	public CollectionWriter() {
		this.separator = DEFAULT_SEPARATOR;
	}

	public CollectionWriter(String separator) {
		this.separator = separator;
	}

	public String buildCollectionString(Collection<? extends Object> objects) {
		String string = "";
		StringBuilder builder = new StringBuilder();
		Iterator<? extends Object> iterator = objects.iterator();
		
		while (iterator.hasNext()) {
			Object object = iterator.next();
			builder.append(object);
			
			if (iterator.hasNext()) {
				builder.append(separator);
			}
		}
		
		string = builder.toString();
		return string;
	}
	
}
