package com.dereekb.gae.server.search.document.readers.impl;

public class SearchDocumentLongIdentifierReader extends AbstractSearchDocumentIdentifierReader<Long> {

	@Override
	protected Long convertIdentifierAtom(String atom) {
		Long value = new Long(atom);
		return value;
	}

}
