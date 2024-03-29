package com.dereekb.gae.server.taskqueue.scheduler.utility.filter;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequestTiming;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequestTimingType;
import com.dereekb.gae.server.taskqueue.scheduler.utility.filter.impl.TaskRequestHashBuilderImpl;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.impl.AbstractFilter;

/**
 * Filter that prevents repeat requests by keeping track of request hashes and
 * countdowns associated with those requests.
 * <p>
 * Requests with no countdown time are ignored and pass the filter.
 * <p>
 * The 'accuracy' can be changed, adding a certain amount of leniency when
 * checking times.
 * <p>
 * This filter uses a thread-safe {@link ConcurrentHashMap} to support multiple
 * managers at once.
 *
 * @author dereekb
 */
public class TaskRequestHashFilter extends AbstractFilter<TaskRequest> {

	private Long accuracy;
	private Long defaultCountdown;

	private TaskRequestHashBuilder hashBuilder;
	private ConcurrentHashMap<Integer, Long> countdownMap = new ConcurrentHashMap<Integer, Long>();

	public TaskRequestHashFilter() {
		this(null, 0L);
	}

	public TaskRequestHashFilter(Long defaultCountdown, Long accuracy) {
		this(new TaskRequestHashBuilderImpl(), defaultCountdown, accuracy);
	}

	public TaskRequestHashFilter(TaskRequestHashBuilder hashBuilder, Long defaultCountdown, Long accuracy) {
		this.hashBuilder = hashBuilder;
		this.setDefaultCountdown(defaultCountdown);
		this.setAccuracy(accuracy);
	}

	public TaskRequestHashBuilder getHashBuilder() {
		return this.hashBuilder;
	}

	public void setHashBuilder(TaskRequestHashBuilder hashBuilder) {
		this.hashBuilder = hashBuilder;
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

	@Override
	public FilterResult filterObject(TaskRequest request) {
		FilterResult result = FilterResult.PASS;
		Integer hash = this.hashBuilder.readHashCode(request);
		Long countdown = this.readCountdown(request);

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

	/**
	 * Reads the countdown from the request.
	 *
	 * @param request
	 * @return the countdown, or null if there is none present.
	 */
	private Long readCountdown(TaskRequest request) {
		Long countdown = null;

		TaskRequestTiming timings = request.getTimings();

		if (timings != null) {
			if (TaskRequestTimingType.COUNTDOWN == timings.getTimingType()) {
				countdown = timings.getTime();
			}
		}

		return countdown;
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

	@Override
	public String toString() {
		return "TaskRequestHashFilter [hashBuilder=" + this.hashBuilder + ", countdownMap=" + this.countdownMap
		        + ", defaultCountdown=" + this.defaultCountdown + ", accuracy=" + this.accuracy + "]";
	}

}
