package com.dereekb.gae.server.storage.object.path.impl;

import com.dereekb.gae.server.storage.object.file.Storable;
import com.dereekb.gae.server.storage.object.file.StorableFile;
import com.dereekb.gae.server.storage.object.file.impl.StorableFileImpl;
import com.dereekb.gae.server.storage.object.folder.FolderPathResolver;
import com.dereekb.gae.server.storage.object.folder.StorableFolder;
import com.dereekb.gae.server.storage.object.path.FilePathMerger;
import com.dereekb.gae.server.storage.object.path.RelativeFilePathResolver;

/**
 * Default implementation of {@link RelativeFilePathResolver} that uses a
 * {@link FolderPathResolver}.
 *
 * @author dereekb
 */
public final class RelativeFilePathResolverImpl<T>
        implements RelativeFilePathResolver<T> {

	private FolderPathResolver<T> folderPathResolver;
	private FilePathMerger pathMerger;

	public RelativeFilePathResolverImpl(FolderPathResolver<T> folderPathResolver) {
		this.folderPathResolver = folderPathResolver;
		this.pathMerger = new FilePathMergerImpl();
	}

	public RelativeFilePathResolverImpl(FolderPathResolver<T> folderPathResolver, FilePathMerger pathMerger) {
		this.folderPathResolver = folderPathResolver;
		this.pathMerger = pathMerger;
	}

	public FolderPathResolver<T> getFolderPathResolver() {
		return this.folderPathResolver;
	}

	public void setFolderPathResolver(FolderPathResolver<T> folderPathResolver) {
		this.folderPathResolver = folderPathResolver;
	}

	public FilePathMerger getPathMerger() {
		return this.pathMerger;
	}

	public void setPathMerger(FilePathMerger pathMerger) {
		this.pathMerger = pathMerger;
	}

	// MARK: RelativeFilePathResolver
	@Override
	public StorableFile pathForStorable(T relative,
	                                    Storable reference) {
		StorableFolder folder = this.folderPathResolver.folderForObject(relative);
		String filename = reference.getFilename();
		String filePath = this.pathMerger.makePath(folder, reference);
		return new StorableFileImpl(filename, filePath);
	}

	@Override
	public String toString() {
		return "RelativeFilePathResolverImpl [folderPathResolver=" + this.folderPathResolver + ", pathMerger="
		        + this.pathMerger + "]";
	}

}
