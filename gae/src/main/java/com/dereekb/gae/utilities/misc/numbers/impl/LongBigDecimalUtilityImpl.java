package com.dereekb.gae.utilities.misc.numbers.impl;

import java.math.BigDecimal;

import com.dereekb.gae.utilities.misc.numbers.DecimalPrecisionLostException;
import com.dereekb.gae.utilities.misc.numbers.LongBigDecimalUtility;

/**
 * Utility for converting an Long "encoded" as a big decimal  
 * @author dereekb
 *
 */
public class LongBigDecimalUtilityImpl extends AbstractBigDecimalUtility<Long> implements LongBigDecimalUtility {

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
			return input.multiply(this.getFactor()).longValueExact();
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

