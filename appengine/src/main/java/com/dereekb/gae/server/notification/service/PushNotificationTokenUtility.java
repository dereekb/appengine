package com.dereekb.gae.server.notification.service;

import java.util.ArrayList;
import java.util.List;

public class PushNotificationTokenUtility {

	public static List<String> readTokenStrings(List<PushNotificationToken> tokens) {
		List<String> tokenStrings = new ArrayList<String>();

		for (PushNotificationToken token : tokens) {
			tokenStrings.add(token.getNotificationToken());
		}

		return tokenStrings;
	}

}
