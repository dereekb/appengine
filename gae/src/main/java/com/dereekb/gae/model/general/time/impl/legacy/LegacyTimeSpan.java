package com.dereekb.gae.model.general.time.impl.legacy;

import com.dereekb.gae.model.general.time.TimeSpan;

/**
 * Legacy DTO version of an {@link TimeSpan} entry.
 * <p>
 * Example JSON:
 * <p>
 * <code> {"daysByte":62,"from":660,"to":840}
 *
 * @author dereekb
 */
public class LegacyTimeSpan {

	private Integer daysByte;
	private Integer from;
	private Integer to;

	public LegacyTimeSpan(Integer daysByte, Integer from, Integer to) {
		this.daysByte = daysByte;
		this.from = from;
		this.to = to;
	}

	public Integer getDaysByte() {
		return this.daysByte;
	}

	public void setDaysByte(Integer daysByte) {
		this.daysByte = daysByte;
	}

	public Integer getFrom() {
		return this.from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getTo() {
		return this.to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "LegacyTimeSpan [daysByte=" + this.daysByte + ", from=" + this.from + ", to=" + this.to + "]";
	}

}
