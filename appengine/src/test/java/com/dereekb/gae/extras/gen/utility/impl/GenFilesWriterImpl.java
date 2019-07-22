package com.dereekb.gae.extras.gen.utility.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFilesWriter;
import com.dereekb.gae.extras.gen.utility.GenFolder;

/**
 * {@link GenFilesWriter} implementation.
 *
 * @author dereekb
 *
 */
public class GenFilesWriterImpl
        implements GenFilesWriter {

	private boolean enforceLowerCase = true;
	private WriteFolder rootFolder;

	public GenFilesWriterImpl() throws IOException {
		this("target/extras/gen");
	}

	public GenFilesWriterImpl(String folderPath) throws IOException {
		this(new File(folderPath));
	}

	public GenFilesWriterImpl(File rootFolder) throws IOException {
		this(new WriteFolder(rootFolder));
	}

	public GenFilesWriterImpl(WriteFolder rootFolder) throws IOException {
		this.setRootFolder(rootFolder);
	}

	public WriteFolder getRootFolder() {
		return this.rootFolder;
	}

	public void setRootFolder(WriteFolder rootFolder) throws IOException {
		if (rootFolder == null) {
			throw new IllegalArgumentException("rootFolder cannot be null.");
		}

		this.rootFolder = rootFolder;
	}

	// MARK: GenFilesWriter
	public void deleteFolder(GenFolder folder) throws IOException {
		new GenFolderWriterImpl(folder, this.rootFolder.getFolderFile()).deleteFolder();
	}

	@Override
	public void writeFiles(GenFolder folder) throws IOException {
		new GenFolderWriterImpl(folder, this.rootFolder).writeFilesAndFolders();
	}

	@Override
	public void writeFilesInFolder(GenFolder folder) throws IOException {
		new GenFolderWriterImpl(folder, this.rootFolder.getFolderFile()).writeFilesAndFolders();
	}

	public static class WriteFolder {

		private File folderFile;

		public WriteFolder(File folderFile) throws IOException {
			makeDirectories(folderFile);
			this.setFolderFile(folderFile);
		}

		public File getFolderFile() {
			return this.folderFile;
		}

		public void setFolderFile(File folderFile) {
			if (folderFile == null) {
				throw new IllegalArgumentException("folderFile cannot be null.");
			}

			this.folderFile = folderFile;
		}

		// Make
		/**
		 * Returns a new fresh folder with the given name under the temporary
		 * folder.
		 */
		public File newFolder(String folder) throws IOException {
			File newFolder = new File(this.folderFile, folder);
			makeDirectory(newFolder);
			return newFolder;
		}

		public static void makeDirectory(File newFolder) throws IOException {
			if (!newFolder.exists() && !newFolder.mkdir()) {
				throw new IOException("Failed creating folder.");
			}
		}

		public static void makeDirectories(File newFolder) throws IOException {
			if (!newFolder.exists() && !newFolder.mkdirs()) {
				throw new IOException("Failed creating folders.");
			}
		}

		public File newFile(String file) {
			return new File(this.folderFile, file);
		}

	}

	private class GenFolderWriterImpl {

		private final GenFolder folder;
		private WriteFolder outputFolder;

		public GenFolderWriterImpl(GenFolder folder, WriteFolder parentFolder) throws IOException {
			this(folder, parentFolder.newFolder(getFileNameString(folder.getFolderName())));
		}

		public GenFolderWriterImpl(GenFolder folder, File folderFile) throws IOException {
			super();
			this.folder = folder;
			this.outputFolder = new WriteFolder(folderFile);
		}

		public void deleteFolder() throws IOException {
			File file = this.outputFolder.getFolderFile();

			if (file.isDirectory() && file.exists()) {
				file.delete();
			}
		}

		public void writeFilesAndFolders() throws IOException {
			this.writeFolders();
			this.writeFiles();
		}

		public void writeFolders() throws IOException {
			for (GenFolder folder : this.folder.getFolders()) {
				new GenFolderWriterImpl(folder, this.outputFolder).writeFilesAndFolders();
			}
		}

		public void writeFiles() throws IOException {
			for (GenFile file : this.folder.getFiles()) {
				new GenFileWriterImpl(file, this.outputFolder).writeFile();
			}
		}

	}

	private class GenFileWriterImpl {

		private final GenFile file;
		private final File outputFile;

		public GenFileWriterImpl(GenFile file, WriteFolder outputFolder) throws IOException {
			this(file, outputFolder.newFile(getFileNameString(file.getOutputFileName())));
		}

		public GenFileWriterImpl(GenFile file, File outputFile) {
			super();
			this.file = file;
			this.outputFile = outputFile;
		}

		public void writeFile() throws UnsupportedOperationException, IOException {
			FileWriter fileWriter = new FileWriter(this.outputFile);
			fileWriter.write(this.file.getFileStringContents());
			fileWriter.close();
		}

	}

	protected String getFileNameString(String fileName) {
		if (this.enforceLowerCase) {
			return fileName.toLowerCase();
		}

		return fileName;
	}

}
