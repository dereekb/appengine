package com.dereekb.gae.test.applications.api.api.upload.util;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.storage.upload.reader.UploadedFile;
import com.dereekb.gae.server.storage.upload.reader.UploadedFileSet;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;


public class TestUploadedFileSetImpl
        implements UploadedFileSet {

	private HashMapWithList<String, UploadedFile> files;

	public TestUploadedFileSetImpl() {
		this(new HashMapWithList<String, UploadedFile>());
	}

	public TestUploadedFileSetImpl(HashMapWithList<String, UploadedFile> files) {
		this.files = files;
	}

	public HashMapWithList<String, UploadedFile> getFiles() {
		return this.files;
	}

	public void setFiles(HashMapWithList<String, UploadedFile> files) {
		this.files = files;
	}

	public void addItem(byte[] bytes,
	                    String filename,
	                    String contentType) {
		TestUploadedFileImpl file = new TestUploadedFileImpl(bytes, filename, contentType);
		this.files.add(filename, file);
	}

	// MARK: UploadedFileSet
	@Override
    public Set<String> getUploadedFileNames() {
		return this.files.keySet();
    }

	@Override
    public HashMapWithList<String, UploadedFile> getUploadedFilesMap() {
		return this.files;
    }

	@Override
    public List<UploadedFile> getAllUploadedFiles() {
		return this.files.valuesList();
    }

	@Override
    public void deleteSetData() {
		// Does nothing.
    }

}
