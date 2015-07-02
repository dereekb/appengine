package com.dereekb.gae.model.stored.blob.generator;

import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * Implementation of {@link Generator} for {@link StoredBlob}.
 *
 * @author dereekb
 */
public final class StoredBlobGenerator extends AbstractGenerator<StoredBlob> {

	private final Factory<String> infoTypeFactory;

	public StoredBlobGenerator(Factory<String> infoTypeFactory) {
		this.infoTypeFactory = infoTypeFactory;
	}

	@Override
	public StoredBlob generate() {
		StoredBlob storedBlob = new StoredBlob();

		storedBlob.setIdentifier(this.randomPositiveLong());

		if (this.infoTypeFactory != null) {
			String infoType = this.infoTypeFactory.make();
			storedBlob.setInfoType(infoType);
			storedBlob.setInfoIdentifier(this.randomPositiveLong().toString());
		}

		return storedBlob;
	}

}
