package com.dereekb.gae.server.search.document.readers;

public class SearchDocumentLongIdentifierReader extends AbstractSearchDocumentIdentifierReader<Long> {

	@Override
	protected Long convertIdentifierAtom(String atom) {
		Long value = new Long(atom);
		return value;
	}

}
