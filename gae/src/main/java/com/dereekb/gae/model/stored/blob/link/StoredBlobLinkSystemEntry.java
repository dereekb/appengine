package com.dereekb.gae.model.stored.blob.link;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.model.crud.services.CrudService;
import com.dereekb.gae.model.crud.services.components.DeleteService;
import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.impl.link.DescribedModelLinkInfo;
import com.dereekb.gae.model.extension.links.components.system.LinkSystemEntry;
import com.dereekb.gae.model.extension.links.impl.AbstractDescriptiveModelLinkSystemEntry;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.server.datastore.Updater;

/**
 * {@link LinkSystemEntry} implementation for {@link StoredBlob}.
 *
 * @author dereekb
 *
 */
public class StoredBlobLinkSystemEntry extends AbstractDescriptiveModelLinkSystemEntry<StoredBlob> {

	public static final String STORED_BLOB_LINK_TYPE = "StoredBlob";

	public StoredBlobLinkSystemEntry(CrudService<StoredBlob> crudService,
	        Updater<StoredBlob> updater,
	        List<DescribedModelLinkInfo> info) {
		super(STORED_BLOB_LINK_TYPE, crudService, crudService, updater, info);
	}

	public StoredBlobLinkSystemEntry(ReadService<StoredBlob> readService,
	        DeleteService<StoredBlob> deleteService,
	        Updater<StoredBlob> updater) {
		super(STORED_BLOB_LINK_TYPE, readService, deleteService, updater);
	}

	public StoredBlobLinkSystemEntry(ReadService<StoredBlob> readService,
	        DeleteService<StoredBlob> deleteService,
	        Updater<StoredBlob> updater,
	        List<DescribedModelLinkInfo> info) {
		super(STORED_BLOB_LINK_TYPE, readService, deleteService, updater, info);
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
		        + this.modelType + ", indexService=" + this.readService + ", updater=" + this.updater + ", reviewer="
		        + this.reviewer + ", validator=" + this.validator + ", reverseLinkNames=" + this.getReverseLinkNames()
		        + "]";
	}

}
