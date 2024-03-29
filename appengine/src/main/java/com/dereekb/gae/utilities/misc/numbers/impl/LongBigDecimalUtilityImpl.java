package com.dereekb.gae.utilities.misc.numbers.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.dereekb.gae.utilities.misc.numbers.DecimalPrecisionLostException;
import com.dereekb.gae.utilities.misc.numbers.LongBigDecimalUtility;

/**
 * Utility for converting an Long "encoded" as a big decimal
 * 
 * @author dereekb
 *
 */
public class LongBigDecimalUtilityImpl extends AbstractBigDecimalUtility<Long>
        implements LongBigDecimalUtility {

	public static final LongBigDecimalUtility THREE_PRECISION_UTILITY = new LongBigDecimalUtilityImpl(3);

	public LongBigDecimalUtilityImpl() {
		super();
	}

	public LongBigDecimalUtilityImpl(Integer precision) {
		super(precision);
	}

	// MARK: AbstractBigDecimalUtility
	@Override
	public Long fromDecimal(BigDecimal input) {
		if (input != null) {
			BigDecimal bigLong = input.multiply(this.getFactor());

			try {
				return bigLong.longValueExact();
			} catch (ArithmeticException e) {
				bigLong = bigLong.setScale(this.getPrecision(), RoundingMode.HALF_UP);
				return bigLong.longValueExact();
			}
		}

		return null;
	}

	@Override
	public BigDecimal toDecimal(Long input) throws DecimalPrecisionLostException {
		if (input != null) {
			return new BigDecimal(input).divide(this.getFactor());
		}

		return null;
	}

}
