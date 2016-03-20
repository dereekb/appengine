package com.dereekb.gae.test.model.stored;

import java.io.IOException;
import java.util.List;

import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.ModelGenerator;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.StoredBlobType;
import com.dereekb.gae.server.datastore.Setter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.storage.accessor.StorageSystem;
import com.dereekb.gae.server.storage.object.file.impl.StorableContentImpl;
import com.dereekb.gae.test.model.extension.generator.TestModelGeneratorImpl;
import com.dereekb.gae.test.model.extension.generator.data.TestByteDataGenerator;
import com.dereekb.gae.test.model.extension.generator.data.TestImageByteGenerator;

/**
 * Generates {@link StoredBlob} values that also have bytes attached.
 *
 * @author dereekb
 *
 */
public class StoredBlobTestGenerator extends TestModelGeneratorImpl<StoredBlob> {

	private StoredBlobType type = StoredBlobType.BLOB;

	private StorageSystem storageSystem;
	private TestByteDataGenerator byteGenerator;

	public StoredBlobTestGenerator(Setter<StoredBlob> setter,
	        ModelGenerator<StoredBlob> generator,
	        StorageSystem storageSystem) {
		this(setter, generator, storageSystem, new TestImageByteGenerator());
	}

	public StoredBlobTestGenerator(Setter<StoredBlob> setter,
	        ModelGenerator<StoredBlob> generator,
	        StorageSystem storageSystem,
	        TestByteDataGenerator byteGenerator) {
		super(setter, generator);
		this.storageSystem = storageSystem;
		this.byteGenerator = new TestImageByteGenerator();
	}

	public StoredBlobType getType() {
		return this.type;
	}

	public void setType(StoredBlobType type) {
		this.type = type;
	}

	public StorageSystem getStorageSystem() {
		return this.storageSystem;
	}

	public void setStorageSystem(StorageSystem storageSystem) {
		this.storageSystem = storageSystem;
	}

	public TestByteDataGenerator getByteGenerator() {
		return this.byteGenerator;
	}

	public void setByteGenerator(TestByteDataGenerator byteGenerator) {
		this.byteGenerator = byteGenerator;
    }

	// MARK: Generate
	@Override
	public StoredBlob generate() {
		StoredBlob model = this.generator.generate();
		this.makeStored(model);
		this.setter.save(model, false);
		return model;
	}

	@Override
	public StoredBlob generate(GeneratorArg arg) {
		StoredBlob model = this.generator.generate(arg);
		this.makeStored(model);
		this.setter.save(model, false);
		return model;
	}

	@Override
	public List<StoredBlob> generate(int count,
	                                 GeneratorArg arg) {
		List<StoredBlob> models = this.generator.generate(count, arg);
		this.makeStored(models);
		this.setter.save(models, false);
		return models;
	}

	@Override
	public StoredBlob generateModel(ModelKey identifier) {
		StoredBlob model = this.generator.generateModel(identifier);
		this.makeStored(model);
		this.setter.save(model, false);
		return model;
	}

	@Override
	public List<StoredBlob> generateModels(Iterable<ModelKey> identifiers) {
		List<StoredBlob> models = this.generator.generateModels(identifiers);
		this.makeStored(models);
		this.setter.save(models, false);
		return models;
	}

	private void makeStored(List<StoredBlob> models) {
		for (StoredBlob model : models) {
			this.makeStored(model);
		}
	}

	private void makeStored(StoredBlob model) {

		model.setBlobType(this.type);
		String contentType = this.type.getMimeType();
		byte[] data = this.byteGenerator.generateBytes();

		StorableContentImpl content = new StorableContentImpl(model, data, contentType);

		try {
			this.storageSystem.saveFile(content);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
