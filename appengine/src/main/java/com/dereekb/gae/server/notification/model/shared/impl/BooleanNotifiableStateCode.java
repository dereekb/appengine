package com.dereekb.gae.server.notification.model.shared.impl;

import com.dereekb.gae.server.notification.model.shared.NotifiableStateCode;

/**
 * {@link NotificationStateCode} that has an either none or sent state.
 *
 * @author dereekb
 *
 */
public enum BooleanNotifiableStateCode implements NotifiableStateCode {

	NONE(0),

	NOTIFIED(1);

	public final int code;

	private BooleanNotifiableStateCode(int code) {
		this.code = code;
	}

	// MARK: IndexCoded
	@Override
	public Integer getStateCode() {
		return this.code;
	}

	public static BooleanNotifiableStateCode valueOf(Boolean id) {
		return (id) ? BooleanNotifiableStateCode.NOTIFIED : BooleanNotifiableStateCode.NONE;
	}

	public static BooleanNotifiableStateCode valueOf(Integer id) {
		BooleanNotifiableStateCode type = BooleanNotifiableStateCode.NONE;

		switch (id) {
			case 0:
				type = BooleanNotifiableStateCode.NONE;
				break;
			case 1:
				type = BooleanNotifiableStateCode.NOTIFIED;
				break;
		}

		return type;
	}

}
