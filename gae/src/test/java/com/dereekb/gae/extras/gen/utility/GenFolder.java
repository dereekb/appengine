package com.dereekb.gae.extras.gen.utility;

import java.util.List;

public interface GenFolder {

	public String getFolderName();

	public List<GenFolder> getFolders();

	public List<GenFile> getFiles();

}
