package com.dereekb.gae.utilities.time.model;

import java.util.Comparator;
import java.util.Date;

/**
 * {@link Comparator} for {@link DatedModel} that compares dates in descending
 * order (Newest to Oldest).
 * 
 * @author dereekb
 *
 */
public class DatedModelDescendingComparator
        implements Comparator<DatedModel> {

	@Override
	public int compare(DatedModel o1,
	                   DatedModel o2) {

		Date a = o1.getDate();
		Date b = o2.getDate();

		return b.compareTo(a);
	}

}
