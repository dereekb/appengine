package com.dereekb.gae.model.stored.blob.generator;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.impl.AbstractModelGenerator;
import com.dereekb.gae.model.extension.generation.impl.keys.LongModelKeyGenerator;
import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Implementation of {@link Generator} for {@link StoredBlob}.
 *
 * @author dereekb
 */
public final class StoredBlobGenerator extends AbstractModelGenerator<StoredBlob> {

	private Generator<Descriptor> descriptorGenerator;

	public StoredBlobGenerator() {
		super(LongModelKeyGenerator.GENERATOR);
	}

	public Generator<Descriptor> getDescriptorGenerator() {
		return this.descriptorGenerator;
	}

	public void setDescriptorGenerator(Generator<Descriptor> descriptorGenerator) {
		this.descriptorGenerator = descriptorGenerator;
	}

	@Override
	public StoredBlob generateModel(ModelKey key,
	                                Long seed) {
		StoredBlob storedBlob = new StoredBlob();
		storedBlob.setIdentifier(ModelKey.readIdentifier(key));

		// TODO: Complete this generator.

		if (this.descriptorGenerator != null) {
			storedBlob.setDescriptor(this.descriptorGenerator.generate(seed));
		}

		return storedBlob;
	}

}
