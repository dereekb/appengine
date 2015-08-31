package com.dereekb.gae.server.storage.gcs.deprecated.blobstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.storage.gcs.deprecated.blobstore.upload.UploadedBlobFile;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.FileInfo;

/**
 * Helper class for reading file information about blobs uploaded through the
 * GAE Blobstore service.
 *
 * @author dereekb
 * @see <a href=
 *      "https://cloud.google.com/appengine/docs/java/blobstore/#Java_Uploading_a_blob">Uploading
 *      a Blob on Google App Engine</a>
 */
public final class BlobstoreUploadedBlobReader {

	private BlobstoreService blobstoreService;

	public BlobstoreUploadedBlobReader() {
		this(BlobstoreServiceFactory.getBlobstoreService());
	}

	public BlobstoreUploadedBlobReader(BlobstoreService blobstoreService) {
		super();
		this.blobstoreService = blobstoreService;
	}

	public Map<String, List<FileInfo>> uploadedFileInfoMap(HttpServletRequest request) {
		Map<String, List<FileInfo>> info = this.blobstoreService.getFileInfos(request);

		return info;
	}

	public List<FileInfo> uploadedFileInfoList(String name,
	                                           HttpServletRequest request) {
		Map<String, List<FileInfo>> info = this.blobstoreService.getFileInfos(request);
		List<FileInfo> infoList = info.get(name);
		return infoList;
	}

	public List<FileInfo> allUploadedFileInfoList(HttpServletRequest request) {
		Map<String, List<FileInfo>> infoMap = this.uploadedFileInfoMap(request);
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();

		for (List<FileInfo> infoList : infoMap.values()) {
			fileInfoList.addAll(infoList);
		}

		return fileInfoList;
	}

	public List<String> uploadedFilenames(String name,
	                                      HttpServletRequest request) {
		List<FileInfo> fileInfoList = this.uploadedFileInfoList(name, request);
		List<String> filenames = new ArrayList<String>();

		for (FileInfo info : fileInfoList) {
			String filename = info.getGsObjectName();
			filenames.add(filename);
		}

		return filenames;
	}

	public List<String> allUploadedFilenames(HttpServletRequest request) {
		List<FileInfo> fileInfoList = this.allUploadedFileInfoList(request);
		List<String> filenames = new ArrayList<String>();

		for (FileInfo info : fileInfoList) {
			String filename = info.getGsObjectName();
			filenames.add(filename);
		}

		return filenames;
	}

	private UploadedBlobFile createUploadedBlobFile(BlobKey key,
	                                                FileInfo info) {
		UploadedBlobFile file = new UploadedBlobFile(key, info);
		return file;
	}

	public List<UploadedBlobFile> createUploadsBlobFiles(List<BlobKey> keys,
	                                                     List<FileInfo> info) {
		List<UploadedBlobFile> blobfiles = new ArrayList<UploadedBlobFile>();

		int size = keys.size();
		for (int i = 0; i < size; i += 1) {
			BlobKey key = keys.get(i);
			FileInfo fileInfo = info.get(i);
			UploadedBlobFile file = this.createUploadedBlobFile(key, fileInfo);
			blobfiles.add(file);
		}

		return blobfiles;
	}

	public Map<String, List<UploadedBlobFile>> uploadedBlobFilesMap(HttpServletRequest request) {
		Map<String, List<BlobKey>> uploads = this.blobstoreService.getUploads(request);
		Map<String, List<FileInfo>> fileinfos = this.blobstoreService.getFileInfos(request);

		Map<String, List<UploadedBlobFile>> uploadMap = new HashMap<String, List<UploadedBlobFile>>();
		Set<String> uploadNames = uploads.keySet();

		for (String name : uploadNames) {
			List<BlobKey> keys = uploads.get(name);
			List<FileInfo> fileinfo = fileinfos.get(name);

			List<UploadedBlobFile> uploadsBlobFiles = this.createUploadsBlobFiles(keys, fileinfo);
			uploadMap.put(name, uploadsBlobFiles);
		}

		return uploadMap;
	}

	public List<UploadedBlobFile> uploadedBlobFilesList(HttpServletRequest request) {
		Map<String, List<BlobKey>> uploads = this.blobstoreService.getUploads(request);
		Map<String, List<FileInfo>> fileinfos = this.blobstoreService.getFileInfos(request);

		List<UploadedBlobFile> blobfiles = new ArrayList<UploadedBlobFile>();
		Set<String> uploadNames = uploads.keySet();

		for (String name : uploadNames) {
			List<BlobKey> keys = uploads.get(name);
			List<FileInfo> fileinfo = fileinfos.get(name);

			List<UploadedBlobFile> uploadsBlobFiles = this.createUploadsBlobFiles(keys, fileinfo);
			blobfiles.addAll(uploadsBlobFiles);
		}

		return blobfiles;
	}

	public List<UploadedBlobFile> uploadedBlobFilesFromMap(Map<String, List<UploadedBlobFile>> map) {
		List<UploadedBlobFile> blobfiles = new ArrayList<UploadedBlobFile>();

		for (List<UploadedBlobFile> files : map.values()) {
			blobfiles.addAll(files);
		}

		return blobfiles;
	}

	public List<BlobKey> getBlobKeysFromFiles(List<UploadedBlobFile> files) {
		List<BlobKey> keys = new ArrayList<BlobKey>();

		for (UploadedBlobFile file : files) {
			BlobKey key = file.getKey();
			keys.add(key);
		}

		return keys;
	}

	public void deleteUploadedFiles(List<UploadedBlobFile> files) {
		List<BlobKey> keys = this.getBlobKeysFromFiles(files);

		BlobKey[] keysArray = new BlobKey[keys.size()];
		keys.toArray(keysArray);
		this.blobstoreService.delete(keysArray);
	}

	public BlobstoreService getBlobstoreService() {
		return this.blobstoreService;
	}

	public void setBlobstoreService(BlobstoreService blobstoreService) {
		this.blobstoreService = blobstoreService;
	}

}
