package com.dereekb.gae.test.extras.gen.utility.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.test.extras.gen.utility.GenFile;
import com.dereekb.gae.test.extras.gen.utility.GenFolder;

public class GenFolderImpl
        implements GenFolder {

	private String folderName;
	private List<GenFolder> folders;
	private List<GenFile> files;

	public GenFolderImpl(String folderName) {
		this(folderName, new ArrayList<>(), new ArrayList<>());
	}

	public GenFolderImpl(String folderName, List<GenFolder> folders, List<GenFile> files) {
		super();
		this.setFolderName(folderName);
		this.setFolders(folders);
		this.setFiles(files);
	}

	@Override
	public String getFolderName() {
		return this.folderName;
	}

	public void setFolderName(String folderName) {
		if (folderName == null) {
			throw new IllegalArgumentException("folderName cannot be null.");
		}

		this.folderName = folderName;
	}

	@Override
	public List<GenFolder> getFolders() {
		return this.folders;
	}

	public void setFolders(List<GenFolder> folders) {
		if (folders == null) {
			throw new IllegalArgumentException("folders cannot be null.");
		}

		this.folders = folders;
	}

	@Override
	public List<GenFile> getFiles() {
		return this.files;
	}

	public void setFiles(List<GenFile> files) {
		if (files == null) {
			throw new IllegalArgumentException("files cannot be null.");
		}

		this.files = files;
	}

	// MARK: Folders

	// MARK: Files
	public void addFile(GenFile file) {
		this.files.add(file);
	}

	@Override
	public String toString() {
		return "GenFolderImpl [folderName=" + this.folderName + ", folders=" + this.folders + ", files=" + this.files
		        + "]";
	}

}
