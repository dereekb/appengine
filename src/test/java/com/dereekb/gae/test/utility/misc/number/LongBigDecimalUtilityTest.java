package com.dereekb.gae.test.utility.misc.number;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.utilities.misc.numbers.DecimalPrecisionLostException;
import com.dereekb.gae.utilities.misc.numbers.LongBigDecimalUtility;
import com.dereekb.gae.utilities.misc.numbers.impl.LongBigDecimalUtilityImpl;


public class LongBigDecimalUtilityTest {

	@Test
	public void testDecimalDoubleConversions() throws DecimalPrecisionLostException {
		LongBigDecimalUtility utility = new LongBigDecimalUtilityImpl();
		
		Double d = 2.222d;
		BigDecimal input = new BigDecimal(d);
		Long output = utility.fromDecimal(input);
		BigDecimal result = utility.toDecimal(output);
		
		Assert.assertTrue(result.compareTo(input.setScale(3, RoundingMode.HALF_UP)) == 0);
	}

	@Test
	public void testDecimalStringConversions() throws DecimalPrecisionLostException {
		LongBigDecimalUtility utility = new LongBigDecimalUtilityImpl();
		
		String d = "2.222";
		BigDecimal input = new BigDecimal(d).setScale(3, RoundingMode.HALF_UP);

		Long output = utility.fromDecimal(input);
		BigDecimal result = utility.toDecimal(output);

		Assert.assertTrue(result.equals(input));
	}

	@Test
	public void testNullDecimalStringConversions() throws DecimalPrecisionLostException {
		LongBigDecimalUtility decimal = new LongBigDecimalUtilityImpl();
		
		Assert.assertNull(decimal.fromDecimal(null));
		Assert.assertNull(decimal.toDecimal(null));
	}

}
