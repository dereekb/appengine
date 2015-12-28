package com.dereekb.gae.model.stored.blob.link;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.impl.link.DescribedModelLinkInfo;
import com.dereekb.gae.model.extension.links.components.system.impl.LinkSystemEntry;
import com.dereekb.gae.model.extension.links.impl.AbstractDescriptiveModelLinkSystemEntry;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;

/**
 * {@link LinkSystemEntry} implementation for {@link StoredBlob}.
 *
 * @author dereekb
 *
 */
public class StoredBlobLinkSystemEntry extends AbstractDescriptiveModelLinkSystemEntry<StoredBlob> {

	public static final String STORED_BLOB_LINK_TYPE = "StoredBlob";

	public StoredBlobLinkSystemEntry(ReadService<StoredBlob> service, ConfiguredSetter<StoredBlob> setter) {
		super(STORED_BLOB_LINK_TYPE, service, setter);
	}

	public StoredBlobLinkSystemEntry(ReadService<StoredBlob> service,
	        ConfiguredSetter<StoredBlob> setter,
	        List<DescribedModelLinkInfo> info) {
		super(STORED_BLOB_LINK_TYPE, service, setter, info);
	}

	@Override
	public List<Link> makeDefinedLinksForModel(final StoredBlob model) {
		List<Link> links = Collections.emptyList();

		// No links for this type.

		return links;
	}

	@Override
	public String toString() {
		return "StoredBlobLinkSystemEntry [descriptiveLinkInfo=" + this.descriptiveLinkInfo + ", modelType="
		        + this.modelType + ", indexService=" + this.service + ", setter=" + this.setter + ", reviewer="
		        + this.reviewer + ", validator=" + this.validator + ", reverseLinkNames=" + this.reverseLinkNames + "]";
	}

}
