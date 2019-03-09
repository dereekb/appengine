package com.dereekb.gae.utilities.time;

import java.util.Date;

/**
 * Used for converting ISO80601 times to/from a string.
 * 
 * @author dereekb
 *
 */
public interface IsoTimeConverter {

	public Date convertFromString(String isoString);

	public String convertToString(Date date);

}
