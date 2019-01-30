package com.thevisitcompany.gae.deprecated.model.extension.search.document.search;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.functions.utility.SearchStringToQueryConverter;

/**
 * Used for building a document search request from a string.
 *
 * @author dereekb
 */
@Deprecated
public interface DocumentSearchStringConverter
        extends SearchStringToQueryConverter<DocumentSearchRequest> {

}
