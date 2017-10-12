package com.dereekb.gae.utilities.collections.time.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * How to handle block rounding.
 * 
 * @author dereekb
 *
 */
public enum TimeBlockRounding implements TimeBlockConverter {

	/**
	 * Rounds the time up to the nearest block.
	 */
	CEIL,

	/**
	 * Rounds the time down to the nearest block.
	 */
	FLOOR;

	// MARK: TimeBlockConverter
	@Override
	public Long roundBlockSize(Long time,
	                              Long timeInPeriod) {

		BigDecimal timeDecimal = new BigDecimal(time).setScale(1);
		BigDecimal periodDecimal = new BigDecimal(timeInPeriod).setScale(1);

		RoundingMode roundingMode = null;

		switch (this) {
			case CEIL:
				roundingMode = RoundingMode.CEILING;
				break;
			case FLOOR:
				roundingMode = RoundingMode.FLOOR;
				break;
		}

		BigDecimal result = timeDecimal.divide(periodDecimal, roundingMode);
		return result.setScale(0, roundingMode).longValue();
	}

}
