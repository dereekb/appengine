package com.dereekb.gae.server.taskqueue.deprecated;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import com.dereekb.gae.utilities.filters.impl.AbstractFilter;
import com.dereekb.gae.utilities.filters.FilterResult;

/**
 * Filter that prevents repeat requests by keeping track of request hashes and countdowns associated with those requests.
 *
 * Requests with no countdown time are ignored and pass the filter.
 *
 * The 'accuracy' can be changed, adding a certain amount of leniency when checking times.
 *
 * This filter uses a thread-safe {@link ConcurrentHashMap} to support multiple managers at once.
 *
 * @author dereekb
 */
@Deprecated
public class TaskQueuePushRequestHashFilter extends AbstractFilter<TaskQueuePushRequest> {

	private ConcurrentHashMap<Integer, Long> countdownMap = new ConcurrentHashMap<Integer, Long>();
	private Long defaultCountdown;
	private Long accuracy = 0L;

	@Override
	public FilterResult filterObject(TaskQueuePushRequest object) {
		FilterResult result = FilterResult.PASS;
		Integer hash = object.hashCode();
		Long countdown = object.getCountdown();

		if (this.containsHash(hash)) {
			if (countdown == null && this.defaultCountdown != null) {
				countdown = this.defaultCountdown;
			}

			if (this.cooldownCompleteForHash(hash) == false) {
				result = FilterResult.FAIL;
			} else {
				this.addTime(hash, countdown, true);
			}
		} else if (countdown != null && countdown > this.accuracy) {
			this.addTime(hash, countdown, false);
		}

		return result;
	}

	private void addTime(Integer hash,
	                     Long countdown,
	                     boolean replace) {
		Date now = new Date();
		Long time = now.getTime() + countdown - this.accuracy;

		if (replace) {
			this.countdownMap.replace(hash, time);
		} else {
			this.countdownMap.putIfAbsent(hash, time);
		}
	}

	public ConcurrentHashMap<Integer, Long> getCountdownMap() {
		return this.countdownMap;
	}

	private boolean containsHash(Integer hash) {
		return this.countdownMap.containsKey(hash);
	}

	private boolean cooldownCompleteForHash(Integer hash) {
		boolean completed = false;

		Date now = new Date();
		Long time = now.getTime();
		Long expectedTime = this.countdownMap.get(hash);

		if (time > expectedTime) {
			completed = true;
		}

		return completed;
	}

	public Long getDefaultCountdown() {
		return this.defaultCountdown;
	}

	public void setDefaultCountdown(Long defaultCountdown) {
		this.defaultCountdown = defaultCountdown;
	}

	public Long getAccuracy() {
		return this.accuracy;
	}

	public void setAccuracy(Long accuracy) {
		if (accuracy == null) {
			accuracy = 0L;
		}

		this.accuracy = accuracy;
	}

}
