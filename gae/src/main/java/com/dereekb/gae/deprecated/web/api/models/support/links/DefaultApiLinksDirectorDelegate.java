package com.thevisitcompany.gae.deprecated.web.api.models.support.links;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.thevisitcompany.gae.deprecated.web.response.ApiResponse;
import com.thevisitcompany.gae.model.extension.links.functions.LinksAction;
import com.thevisitcompany.gae.model.extension.links.functions.LinksMethodRetriever;

/**
 * Default implementation of {@link ApiLinksDirectorDelegate} interface, that uses a {@link LinksMethodRetriever} to scan a delegate for
 * methods.
 * 
 * The methods should return an {@link ApiResponse} and take an {@link LinksChange} as a parameter.
 * 
 * @author dereekb
 * @param <K>
 */
public class DefaultApiLinksDirectorDelegate<K>
        implements ApiLinksDirectorDelegate<K> {

	private LinksMethodRetriever retriever = new LinksMethodRetriever();
	private Object delegate;

	@Override
	public ApiResponse handleLinksChange(ApiLinksChange<K> changes) {

		ApiResponse response = null;
		LinksAction action = changes.getAction();
		String type = changes.getChanges().getType();

		Method method = retriever.retrieveMethod(type, action);

		if (method != null) {
			try {
				LinksChange<K> change = changes.getChanges();
				Object result = method.invoke(delegate, change);

				if (result != null) {
					response = (ApiResponse) result;
				} else {
					response = new ApiResponse();
				}
			} catch (IllegalAccessException | IllegalArgumentException e) {
				e.printStackTrace(); // Called only if the method call has wrong args, etc.
			} catch (InvocationTargetException e) {
				Throwable cause = e.getCause();

				// Throw runtime exceptions normally.
				if (cause instanceof RuntimeException) {
					RuntimeException runtime = (RuntimeException) cause;
					throw runtime;
				} else {
					e.printStackTrace();
				}
			}
		}

		return response;
	}

	public Object getDelegate() {
		return delegate;
	}

	public void setDelegate(Object delegate) {
		this.delegate = delegate;
		retriever.setDelegateClass(this.delegate.getClass());
	}

}
