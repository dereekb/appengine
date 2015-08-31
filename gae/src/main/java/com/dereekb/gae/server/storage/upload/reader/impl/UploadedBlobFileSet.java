package com.dereekb.gae.server.storage.upload.reader.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.storage.exception.NoFileDataException;
import com.dereekb.gae.server.storage.gcs.blobstore.BlobstoreBlobReader;
import com.dereekb.gae.server.storage.gcs.blobstore.impl.BlobstoreBlobReaderImpl;
import com.dereekb.gae.server.storage.upload.reader.UploadedFile;
import com.dereekb.gae.server.storage.upload.reader.UploadedFileSet;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.FileInfo;

/**
 * {@link UploadedFileSet} implementation for the Blobstore.
 *
 * @author dereekb
 *
 */
public class UploadedBlobFileSet
        implements UploadedFileSet, UploadedBlobFileDelegate {

	private final BlobstoreService blobstoreService;
	private final HttpServletRequest request;

	private BlobstoreBlobReader reader;
	private HashMapWithList<String, UploadedBlobFile> uploadedFilesMap;

	public UploadedBlobFileSet(BlobstoreService blobstoreService, HttpServletRequest request) {
		this.blobstoreService = blobstoreService;
		this.request = request;

		this.reader = new BlobstoreBlobReaderImpl(blobstoreService);
	}

	public HashMapWithList<String, UploadedBlobFile> getFileInfoMap() {
		if (this.uploadedFilesMap == null) {
			this.uploadedFilesMap = this.buildFileInfoMap();
		}

		return this.uploadedFilesMap;
	}

	private HashMapWithList<String, UploadedBlobFile> buildFileInfoMap() {
		Map<String, List<BlobKey>> uploads = this.blobstoreService.getUploads(this.request);
		Map<String, List<FileInfo>> fileinfos = this.blobstoreService.getFileInfos(this.request);

		HashMapWithList<String, UploadedBlobFile> uploadMap = new HashMapWithList<String, UploadedBlobFile>();
		Set<String> uploadNames = uploads.keySet();

		for (String name : uploadNames) {
			List<BlobKey> keys = uploads.get(name);
			List<FileInfo> fileinfo = fileinfos.get(name);

			List<UploadedBlobFile> uploadsBlobFiles = this.buildUploadedBlobFiles(keys, fileinfo);
			uploadMap.addAll(name, uploadsBlobFiles);
		}

		return uploadMap;
	}

	private List<UploadedBlobFile> buildUploadedBlobFiles(List<BlobKey> keys,
	                                                      List<FileInfo> info) {
		List<UploadedBlobFile> blobfiles = new ArrayList<UploadedBlobFile>();

		int size = keys.size();
		for (int i = 0; i < size; i += 1) {
			BlobKey key = keys.get(i);
			FileInfo fileInfo = info.get(i);
			UploadedBlobFile file = new UploadedBlobFile(this, key, fileInfo);
			blobfiles.add(file);
		}

		return blobfiles;
	}

	public List<BlobKey> getAllBlobKeys() {
		List<BlobKey> keys = new ArrayList<BlobKey>();
		HashMapWithList<String, UploadedBlobFile> map = this.getFileInfoMap();

		for (UploadedBlobFile file : map.getAllElements()) {
			BlobKey key = file.getBlobKey();
			keys.add(key);
		}

		return keys;
	}

	// MARK: UploadedBlobFileDelegate
	@Override
	public byte[] readFileBytes(UploadedBlobFile uploadedBlobFile) throws IOException, NoFileDataException {
		BlobKey key = uploadedBlobFile.getBlobKey();
		Long size = uploadedBlobFile.getSize();
		byte[] bytes = null;

		if (size != null && size > 0) {
			bytes = this.reader.readBlobBytes(key, size);
		} else {
			throw new NoFileDataException();
		}

		return bytes;
	}

	//MARK: UploadedFileSet
	@Override
	public Set<String> getUploadedFileNames() {
		HashMapWithList<String, UploadedBlobFile> map = this.getFileInfoMap();
		return map.getKeySet();
	}

	@Override
	public HashMapWithList<String, UploadedFile> getUploadedFilesMap() {
		return new HashMapWithList<String, UploadedFile>(this.uploadedFilesMap);
	}

	@Override
	public List<UploadedFile> getAllUploadedFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSetData() {
		List<BlobKey> keys = this.getAllBlobKeys();

		BlobKey[] keysArray = new BlobKey[keys.size()];
		keys.toArray(keysArray);

		this.blobstoreService.delete(keysArray);
	}


}
