package com.dereekb.gae.server.search.system;

/**
 * Interface for carrying out searches in the Google App Engine Search API.
 *
 * @author dereekb
 * @see <a
 *      href="https://developers.google.com/appengine/docs/java/search/#Java_Overview">Google
 *      App Engine Search API</a>
 */
public interface SearchDocumentSystem
        extends SearchDocumentIndexSystem, SearchDocumentDeleteSystem, SearchDocumentReadSystem,
        SearchDocumentQuerySystem {

}
