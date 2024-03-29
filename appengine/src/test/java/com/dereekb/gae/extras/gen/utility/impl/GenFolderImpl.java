package com.dereekb.gae.extras.gen.utility.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * {@link GenFolder} implementation.
 *
 * @author dereekb
 *
 */
public class GenFolderImpl
        implements GenFolder {

	private static String DEFAULT_FOLDER_NAME = "folder";

	private String folderName;
	private List<GenFolder> folders;
	private List<GenFile> files;

	public GenFolderImpl() {
		this(GenFolderImpl.DEFAULT_FOLDER_NAME);
	}

	public GenFolderImpl(String folderName) {
		this(folderName, new ArrayList<>(), new ArrayList<>());
	}

	public GenFolderImpl(String folderName, List<GenFolder> folders, List<GenFile> files) {
		super();
		this.setFolderName(folderName);
		this.setFolders(folders);
		this.setFiles(files);
	}

	public GenFolderImpl(GenFolder folder) {
		this(folder.getFolderName(), folder);
	}

	public GenFolderImpl(String folderName, GenFolder folder) {
		this(folderName, ListUtility.copy(folder.getFolders()), ListUtility.copy(folder.getFiles()));
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
	@Override
	public boolean hasFileWithName(String filename) {
		return this.getFileWithName(filename) != null;
	}

	@Override
	public GenFile getFileWithName(String filename) {
		for (GenFile file : this.getFiles()) {
			if (file.getFileName().equalsIgnoreCase(filename)) {
				return file;
			}
		}

		return null;
	}

	public void merge(List<GenFolder> folders) {
		for (GenFolder folder : folders) {
			this.merge(folder);
		}
	}

	public void safeMerge(GenFolder folder) {
		if (folder != null) {
			this.merge(folder);
		}
	}

	public void merge(GenFolder folder) {
		this.addFolders(folder.getFolders());
		this.addFiles(folder.getFiles());
	}

	public void flatten() {
		this.flatten(false);
	}

	public void flatten(boolean recursive) {
		List<GenFolder> folders = this.folders;

		if (recursive) {
			folders = new ArrayList<GenFolder>();

			for (GenFolder folder : folders) {
				GenFolderImpl copy = new GenFolderImpl(folder);
				copy.flatten(recursive);
				folders.add(copy);
			}
		}

		this.folders = new ArrayList<GenFolder>();

		for (GenFolder folder : folders) {
			this.merge(folder);
		}
	}

	public void safeAddFolder(GenFolder folder) {
		if (folder != null) {
			this.addFolder(folder);
		}
	}

	public void addFolder(GenFolder folder) {
		if (folder == this) {
			throw new IllegalArgumentException("Cannot add self.");
		}

		this.folders.add(folder);
	}

	public void addFolders(List<GenFolder> folders) {
		this.folders.addAll(folders);
	}

	@Override
	public GenFolderImpl wrap(String folder) {
		GenFolderImpl wrap = new GenFolderImpl(folder);

		wrap.addFolder(this);

		return wrap;
	}

	// MARK: Files
	public void safeAddFile(GenFile file) {
		if (file != null) {
			this.addFile(file);
		}
	}

	public void addFile(GenFile file) {
		this.files.add(file);
	}

	public void addFiles(List<GenFile> files) {
		this.files.addAll(files);
	}

	@Override
	public String toString() {
		return "GenFolderImpl [folderName=" + this.folderName + ", folders=" + this.folders + ", files=" + this.files
		        + "]";
	}

}
