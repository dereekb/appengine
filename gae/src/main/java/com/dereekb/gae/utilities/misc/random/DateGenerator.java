package com.dereekb.gae.utilities.misc.random;

import java.util.Date;

import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.utilities.time.DateUtility;

/**
 * {@link Generator} for {@link Date} that generates a date relative to the
 * current time.
 *
 * @author dereekb
 *
 */
public class DateGenerator extends AbstractGenerator<Date> {

	private static final Long DEFAULT_MAX_LENGTH = DateUtility.timeInDays(10);

	private Date min = new Date(0);
	private Long maxLength = DEFAULT_MAX_LENGTH;

	public DateGenerator() {}

	public DateGenerator(Date min, Long maxLength) {
		this.setMin(min);
		this.setMaxLength(maxLength);
	}

	public Date getMin() {
		return this.min;
	}

	public void setMinDateAutoGenerate() {
		this.setMin(null);
	}
	
	public void setMinDateAlwaysFromZero() {
		this.setMin(new Date(0));
	}

	public void setMin(Date min) {
		this.min = min;
	}

	public Long getMaxLength() {
		return this.maxLength;
	}

	public void setMaxLength(Long maxLength) throws IllegalArgumentException {
		if (maxLength == null) {
			throw new IllegalArgumentException("MaxLength cannot be null.");
		}

		this.maxLength = maxLength;
	}

	public void setMaxLengthInDays(Long days) throws IllegalArgumentException {
		if (days == null) {
			throw new IllegalArgumentException("Days cannot be null.");
		}

		this.setMaxLength(DateUtility.timeInDays(days));
	}

	public Date generateMinDate() {
		if (this.min == null) {
			return new Date();
		} else {
			return this.min;
		}
	}

	// MARK: AbstractGenerator
	@Override
	public Date generate(GeneratorArg arg) {
		Long timeSpan = PositiveLongGenerator.GENERATOR.generate(arg) % this.maxLength;
		Date date = new Date(this.generateMinDate().getTime() + timeSpan);
		return date;
	}

	@Override
	public String toString() {
		return "DateGenerator [min=" + this.min + ", maxLength=" + this.maxLength + "]";
	}

}
