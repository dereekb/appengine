package com.dereekb.gae.model.stored.blob.link;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkSystemBuilderEntry;
import com.dereekb.gae.model.extension.links.system.mutable.impl.AbstractDescriptiveMutableLinkSystemBuilderEntry;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;

/**
 * {@link MutableLinkSystemBuilderEntry} implementation for {@link StoredBlob}.
 *
 * @author dereekb
 *
 */
public class StoredBlobLinkSystemBuilderEntry extends AbstractDescriptiveMutableLinkSystemBuilderEntry<StoredBlob> {

	public static final String STORED_BLOB_LINK_TYPE = "StoredBlob";

	public StoredBlobLinkSystemBuilderEntry(ReadService<StoredBlob> readService,
	        TypeModelKeyConverter typeKeyConverter) {
		super(readService, typeKeyConverter);
	}

	@Override
	public String getLinkModelType() {
		return STORED_BLOB_LINK_TYPE;
	}

	@Override
	public ModelKeyType getModelKeyType() {
		return ModelKeyType.NAME;
	}

	@Override
	protected List<MutableLinkData<StoredBlob>> makeDefinedLinkData() {
		return Collections.emptyList();
	}

	@Override
	public String toString() {
		return "StoredBlobLinkSystemBuilderEntry []";
	}

}
