package com.dereekb.gae.model.stored.blob.generator;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractModelGenerator;
import com.dereekb.gae.model.extension.generation.impl.keys.LongModelKeyGenerator;
import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.StoredBlobType;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Implementation of {@link Generator} for {@link StoredBlob}.
 * <p>
 * Does not generate file path names, storedBlob elements.
 *
 * @author dereekb
 */
public class StoredBlobGenerator extends AbstractModelGenerator<StoredBlob> {

	private String DEFAULT_FILE_PATH_FORMAT = "generated/%s";

	private String filePathFormat = this.DEFAULT_FILE_PATH_FORMAT;
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

	public String getFilePathFormat() {
		return this.filePathFormat;
	}

	public void setFilePathFormat(String filePathFormat) {
		this.filePathFormat = filePathFormat;
	}

	@Override
	public StoredBlob generateModel(ModelKey key,
	                                GeneratorArg arg) {

		// Identifier
		StoredBlob storedBlob = new StoredBlob();
		storedBlob.setIdentifier(ModelKey.readIdentifier(key));

		// Info
		storedBlob.setBlobType(StoredBlobType.BLOB);

		String fileName = key.keyAsString();

		storedBlob.setBlobName(fileName);
		storedBlob.setFilePath(String.format(this.filePathFormat, fileName));

		// Descriptor
		if (this.descriptorGenerator != null) {
			storedBlob.setDescriptor(this.descriptorGenerator.generate(arg));
		}

		return storedBlob;
	}

}
