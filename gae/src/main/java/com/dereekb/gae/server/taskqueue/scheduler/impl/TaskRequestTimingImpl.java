package com.dereekb.gae.server.taskqueue.scheduler.impl;

import com.dereekb.gae.server.taskqueue.scheduler.TaskRequestTiming;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequestTimingType;

/**
 * {@link TaskRequestTiming} implementation.
 *
 * @author dereekb
 */
public final class TaskRequestTimingImpl
        implements TaskRequestTiming {

	private TaskRequestTimingType timingType;

	private Long time;

	public TaskRequestTimingImpl(TaskRequestTimingType timingType, Long time) {
		this.timingType = timingType;
		this.time = time;
	}

	@Override
	public TaskRequestTimingType getTimingType() {
		return this.timingType;
    }

	public void setTimingType(TaskRequestTimingType timingType) {
		this.timingType = timingType;
    }

    @Override
    public Long getTime() {
    	return this.time;
    }

    public void setTime(Long time) {
    	this.time = time;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.time == null) ? 0 : this.time.hashCode());
		result = prime * result + ((this.timingType == null) ? 0 : this.timingType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		TaskRequestTimingImpl other = (TaskRequestTimingImpl) obj;
		if (this.time == null) {
			if (other.time != null) {
				return false;
			}
		} else if (!this.time.equals(other.time)) {
			return false;
		}
		if (this.timingType != other.timingType) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "TaskRequestTimingImpl [timingType=" + this.timingType + ", time=" + this.time + "]";
	}

}
