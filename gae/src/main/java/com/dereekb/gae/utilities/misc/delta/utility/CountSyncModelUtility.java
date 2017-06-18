package com.dereekb.gae.utilities.misc.delta.utility;

import com.dereekb.gae.utilities.misc.delta.CountSyncModel;

public class CountSyncModelUtility {

	public static boolean hasDeltaChange(CountSyncModel<?> model) {
		return model.getDelta() != null;
	}

}
