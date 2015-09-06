package com.dereekb.gae.model.extension.data.storage;

import java.util.Collection;

import com.dereekb.gae.model.extension.data.conversion.bytes.ModelByteDataConverter;
import com.dereekb.gae.model.extension.data.storage.exporter.ExportException;
import com.dereekb.gae.model.extension.data.storage.exporter.Exporter;
import com.dereekb.gae.model.extension.data.storage.importer.ImportException;
import com.dereekb.gae.model.extension.data.storage.importer.Importer;
import com.dereekb.gae.server.storage.StorageAccessor;
import com.dereekb.gae.server.storage.file.StorableData;
import com.dereekb.gae.server.storage.file.impl.StorableContentImpl;
import com.dereekb.gae.server.storage.file.impl.StorableFileImpl;

public class DefaultImporterExporter<T>
        implements Exporter<T>, Importer<T> {

	private ModelByteDataConverter<T, ?, ?> converter;
	private StorageAccessor accessor;

	@Override
	public void exportObjects(Collection<T> objects,
	                          StorableFileImpl file) throws ExportException {
		try {
			byte[] data = this.converter.convertToBytes(objects);
			String type = this.converter.getByteContentType();
			StorableContentImpl content = new StorableContentImpl(file, data, type);
			this.accessor.saveFile(content);
		} catch (Exception e) {
			throw new ExportException(e);
		}
	}

	@Override
	public Collection<T> importObjects(StorableFileImpl file) throws ImportException {
		Collection<T> objects;

		try {
			StorableData content = this.accessor.loadFile(file);
			byte[] data = content.getFileData();
			objects = this.converter.convertToObjects(data);
		} catch (Exception e) {
			throw new ImportException(e);
		}

		return objects;
	}

	public ModelByteDataConverter<T, ?, ?> getConverter() {
		return this.converter;
	}

	public void setConverter(ModelByteDataConverter<T, ?, ?> converter) {
		this.converter = converter;
	}

	public StorageAccessor getAccessor() {
		return this.accessor;
	}

	public void setAccessor(StorageAccessor accessor) {
		this.accessor = accessor;
	}

}
