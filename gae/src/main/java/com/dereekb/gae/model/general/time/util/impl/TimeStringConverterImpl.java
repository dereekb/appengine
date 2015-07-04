package com.dereekb.gae.model.general.time.util.impl;

import com.dereekb.gae.model.general.time.Time;
import com.dereekb.gae.model.general.time.util.TimeStringConverter;


public class TimeStringConverterImpl
        implements TimeStringConverter {

	public final static String TIME_REGEX = "^(([a-zA-Z])+)$|^((((\\d){1,2})?)((((:)?(\\d){2}))?)(((\\s)?([a-zA-Z])+))?)$";

	@Override
	public String timeToString(Time time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time timeFromString(String timeString) {
		// TODO Auto-generated method stub
		return null;
	}

}
