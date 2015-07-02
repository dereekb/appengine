package com.dereekb.gae.server.storage.path;

import com.dereekb.gae.server.storage.file.Storable;
import com.dereekb.gae.server.storage.file.StorableFile;
import com.dereekb.gae.server.storage.file.StorageFile;
import com.dereekb.gae.server.storage.folder.FolderPathResolver;
import com.dereekb.gae.server.storage.folder.StorableFolder;

/**
 * Default implementation of {@link RelativeFilePathResolver} that uses a
 * {@link FolderPathResolver}.
 *
 * @author dereekb
 */
public final class DefaultRelativeFilePathResolver<T>
        implements RelativeFilePathResolver<T> {

	private final FolderPathResolver<T> folderPathResolver;
	private final FilePathMerger pathMerger;

	public DefaultRelativeFilePathResolver(FolderPathResolver<T> folderPathResolver) {
		this.folderPathResolver = folderPathResolver;
		this.pathMerger = new DefaultFilePathMerger();
	}

	public DefaultRelativeFilePathResolver(FolderPathResolver<T> folderPathResolver, FilePathMerger pathMerger) {
		this.folderPathResolver = folderPathResolver;
		this.pathMerger = pathMerger;
	}

	public FolderPathResolver<T> getFolderPathResolver() {
		return this.folderPathResolver;
	}

	@Override
	public StorableFile pathForStorable(T relative,
	                                    Storable reference) {
		StorableFolder folder = this.folderPathResolver.folderForObject(relative);
		String filename = reference.getFilename();
		String filePath = this.pathMerger.makePath(folder, reference);
		return new StorageFile(filename, filePath);
	}

}
