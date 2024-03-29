package com.dereekb.gae.server.storage.upload.handler.impl.delegate.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.storage.upload.handler.impl.delegate.UploadedFileResult;
import com.dereekb.gae.server.storage.upload.reader.UploadedFile;
import com.dereekb.gae.utilities.collections.pairs.impl.ResultPairImpl;

/**
 * {@link ResultPairImpl} that contains an {@link UploadedFile} and the
 * {@link UploadedFileResult} result.
 *
 * @author dereekb
 *
 */
public class UploadPair extends ResultPairImpl<UploadedFile, UploadedFileResult> {

	public UploadPair(UploadedFile source) {
		super(source);
	}

	public static List<UploadPair> createPairsForFiles(Iterable<UploadedFile> files) {
		List<UploadPair> pairs = new ArrayList<UploadPair>();

		for (UploadedFile file : files) {
			UploadPair pair = new UploadPair(file);
			pairs.add(pair);
		}

		return pairs;
	}

}
