package com.dereekb.gae.utilities.misc.numbers.impl;

import java.math.BigDecimal;

import com.dereekb.gae.utilities.misc.numbers.BigDecimalUtility;
import com.dereekb.gae.utilities.misc.numbers.DecimalPrecisionLostException;

/**
 * Abstract {@link BigDecimalUtility} implementation.
 * 
 * @author dereekb
 *
 * @param <N> number type
 */
public abstract class AbstractBigDecimalUtility<N extends Number> implements BigDecimalUtility<N> {

	public static final Integer DEFAULT_PRECISION = 3;	// 3 Decimal points
	public static final Integer MAX_PRECISION = 10;	// 10 Decimal points
	
	private BigDecimal factor;

	public AbstractBigDecimalUtility() {
		this(DEFAULT_PRECISION);
	}
	
	public AbstractBigDecimalUtility(Integer precision) {
		super();
		this.setPrecision(precision);
	}
	
	// MARK: Accessosrs
	public BigDecimal getFactor() {
		return this.factor;
	}

	private void setPrecision(Integer precision) {
		if (precision == null) {
			throw new IllegalArgumentException("Precision cannot be null.");
		} else if (precision > MAX_PRECISION) {
			throw new IllegalArgumentException("Precision cannot be greater than 10.");
		}
	
		this.factor = new BigDecimal(1).scaleByPowerOfTen(precision);
	}

	// MARK: BigDecimalUtility
	@Override
	public BigDecimal quickToDecimal(N input)  {
		try {
			return this.toDecimal(input);
		} catch (DecimalPrecisionLostException e) {
			return null;
		}
	}

}
