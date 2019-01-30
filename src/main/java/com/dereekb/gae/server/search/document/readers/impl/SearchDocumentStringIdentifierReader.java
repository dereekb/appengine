package com.dereekb.gae.server.search.document.readers.impl;

public class SearchDocumentStringIdentifierReader extends AbstractSearchDocumentIdentifierReader<String> {

	@Override
	protected String convertIdentifierAtom(String atom) {
		return atom;
	}

}
