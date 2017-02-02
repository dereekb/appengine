package com.dereekb.gae.utilities.time.model;

import java.util.Comparator;
import java.util.Date;

/**
 * {@link Comparator} for {@link DatedModel} that compares dates in ascending
 * order (Oldest to Newest).
 * 
 * @author dereekb
 *
 */
public class DatedModelAscendingComparator
        implements Comparator<DatedModel> {

	@Override
	public int compare(DatedModel o1,
	                   DatedModel o2) {

		Date a = o1.getDate();
		Date b = o2.getDate();

		return a.compareTo(b);
	}

}
