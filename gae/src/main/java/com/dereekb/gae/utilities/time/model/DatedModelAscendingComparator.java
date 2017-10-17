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
public class DatedModelAscendingComparator<T extends DatedModel>
        implements Comparator<T> {

	@Override
	public int compare(T o1,
	                   T o2) {

		Date a = o1.getDate();
		Date b = o2.getDate();

		return a.compareTo(b);
	}

}
