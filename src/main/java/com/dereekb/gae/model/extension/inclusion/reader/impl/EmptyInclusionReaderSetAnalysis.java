package com.dereekb.gae.model.extension.inclusion.reader.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.dereekb.gae.model.extension.inclusion.exception.InclusionTypeUnavailableException;
import com.dereekb.gae.model.extension.inclusion.reader.InclusionReaderSetAnalysis;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.set.CaseInsensitiveSet;

/**
 * {@link InclusionReaderSetAnalysis}
 * 
 * @author dereekb
 *
 */
public class EmptyInclusionReaderSetAnalysis
        implements InclusionReaderSetAnalysis {
	
	private static final CaseInsensitiveSet SET = new CaseInsensitiveSet();
	
	public static final EmptyInclusionReaderSetAnalysis SINGLETON = new EmptyInclusionReaderSetAnalysis();

	private EmptyInclusionReaderSetAnalysis() {}
	
	public static EmptyInclusionReaderSetAnalysis make() {
		return SINGLETON;
	}
	
	// MARK: InclusionReaderSetAnalysis
	@Override
	public Collection<ModelKey> getModelKeys() {
		return Collections.emptySet();
	}

	@Override
	public CaseInsensitiveSet getRelatedTypes() {
		return SET;
	}

	@Override
	public Set<ModelKey> getKeysForType(String type) throws InclusionTypeUnavailableException {
		return Collections.emptySet();
	}

}
