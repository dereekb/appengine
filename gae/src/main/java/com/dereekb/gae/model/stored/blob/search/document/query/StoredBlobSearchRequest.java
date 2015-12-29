package com.dereekb.gae.model.stored.blob.search.document.query;

import com.dereekb.gae.model.extension.search.document.search.model.DateSearch;
import com.dereekb.gae.model.extension.search.document.search.model.DescriptorSearch;
import com.dereekb.gae.model.extension.search.document.search.service.model.impl.AbstractModelDocumentRequest;
import com.dereekb.gae.model.stored.blob.StoredBlob;

/**
 * Search request for a {@link StoredBlob}.
 *
 * @author dereekb
 *
 */
public class StoredBlobSearchRequest extends AbstractModelDocumentRequest {

	private String type;
	private String ending;
	private String name;
	private DateSearch date;
	private DescriptorSearch descriptor;

	public StoredBlobSearchRequest() {}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEnding() {
		return this.ending;
	}

	public void setEnding(String ending) {
		this.ending = ending;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DateSearch getDate() {
		return this.date;
    }

    public void setDate(DateSearch date) {
    	this.date = date;
    }

    public DescriptorSearch getDescriptor() {
		return this.descriptor;
    }

    public void setDescriptor(DescriptorSearch descriptor) {
    	this.descriptor = descriptor;
    }

	@Override
	public String toString() {
		return "StoredBlobSearchRequest [type=" + this.type + ", ending=" + this.ending + ", name=" + this.name
		        + ", date=" + this.date + ", descriptor=" + this.descriptor + "]";
	}

}
