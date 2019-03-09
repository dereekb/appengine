package com.dereekb.gae.model.extension.links.components.accessor.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReader;
import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReaderAnalysis;
import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReaderSetAnalysis;
import com.dereekb.gae.model.extension.inclusion.reader.impl.ModelInclusionReaderAnalysisImpl;
import com.dereekb.gae.model.extension.inclusion.reader.impl.ModelInclusionReaderSetAnalysisImpl;
import com.dereekb.gae.model.extension.links.components.LinkData;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.ReadOnlyLink;
import com.dereekb.gae.model.extension.links.components.accessor.LinkReader;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMapWithSet;

/**
 * {@link ModelInclusionReader} implementation.
 *
 * @author dereekb
 *
 */
public class LinkModelInclusionReaderImpl<T extends UniqueModel>
        implements ModelInclusionReader<T> {

	private LinkReader<T> reader;

	public LinkModelInclusionReaderImpl(LinkReader<T> reader) throws IllegalArgumentException {
		this.setReader(reader);
	}

	public LinkReader<T> getReader() {
		return this.reader;
	}

	public void setReader(LinkReader<T> reader) throws IllegalArgumentException {
		if (reader == null) {
			throw new IllegalArgumentException("Reader cannot be null.");
		}

		this.reader = reader;
	}

	// MARK: ModelInclusionReader
	@Override
	public ModelInclusionReaderAnalysis<T> analyzeInclusionsForModel(T model) throws IllegalArgumentException {
		CaseInsensitiveMapWithSet<ModelKey> relations = new CaseInsensitiveMapWithSet<ModelKey>();
		List<? extends ReadOnlyLink> links = this.reader.getLinks(model);

		for (ReadOnlyLink link : links) {
			LinkTarget target = link.getLinkTarget();
			LinkData data = link.getLinkData();

			String type = target.getTargetType();
			List<ModelKey> keys = data.getRelationKeys();

			relations.addAll(type, keys);
		}

		return new ModelInclusionReaderAnalysisImpl<T>(model, relations);
	}

	@Override
	public ModelInclusionReaderSetAnalysis<T> analyzeInclusionsForModels(Collection<T> models)
	        throws IllegalArgumentException {
		return ModelInclusionReaderSetAnalysisImpl.makeSetAnalysis(models, this);
	}

	@Override
	public String toString() {
		return "LinkModelInclusionReaderImpl [reader=" + this.reader + "]";
	}

}
