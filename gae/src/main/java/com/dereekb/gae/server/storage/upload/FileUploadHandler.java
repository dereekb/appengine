package com.dereekb.gae.server.storage.upload;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles reading uploaded data from an HttpRequest, and returning objects based on how the data was handled.
 * 
 * @author dereekb
 * @param <T>
 */
public interface FileUploadHandler<T> {

	/**
	 * Reads upload data from the request, then converts it into new objects of type <T>.
	 * 
	 * @param request
	 * @return List of created objects, or an empty list if nothing was created.
	 */
	public List<T> upload(HttpServletRequest request);

}
