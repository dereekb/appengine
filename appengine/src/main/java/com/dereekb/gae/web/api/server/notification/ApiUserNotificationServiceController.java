package com.dereekb.gae.web.api.server.notification;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.notification.model.token.dto.NotificationTokenData;
import com.dereekb.gae.server.notification.user.service.UserPushNotificationService;
import com.dereekb.gae.web.api.exception.ApiIllegalArgumentException;
import com.dereekb.gae.web.api.exception.resolver.RuntimeExceptionResolver;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * REST controller for updating a user's push notification tokens.
 *
 * @author dereekb
 *
 */
@RestController
@RequestMapping("/notification")
public class ApiUserNotificationServiceController {

	private UserPushNotificationService userPushNotificationService;

	public ApiUserNotificationServiceController(UserPushNotificationService userPushNotificationService) {
		super();
		this.setUserPushNotificationService(userPushNotificationService);
	}

	public UserPushNotificationService getUserPushNotificationService() {
		return this.userPushNotificationService;
	}

	public void setUserPushNotificationService(UserPushNotificationService userPushNotificationService) {
		if (userPushNotificationService == null) {
			throw new IllegalArgumentException("userPushNotificationService cannot be null.");
		}

		this.userPushNotificationService = userPushNotificationService;
	}

	// MARK: Service Controller
	/**
	 * Adds/updates a device for the current user.
	 *
	 * @param device
	 *            {@link NotificationTokenData}. Never {@code null}.
	 * @return {@link ApiResponse}. Never {@code null}.
	 */
	@ResponseBody
	@RequestMapping(value = "/device/add", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ApiResponse schedule(@Valid @RequestBody NotificationTokenData device) {
		ApiResponse response = null;

		ModelKey userKey = LoginSecurityContext.getPrincipal().getLoginKey();

		try {
			this.userPushNotificationService.addDevice(userKey, device);
			response = new ApiResponseImpl();
		} catch (IllegalArgumentException e) {
			throw new ApiIllegalArgumentException(e);
		} catch (RuntimeException e) {
			RuntimeExceptionResolver.resolve(e);
		}

		return response;
	}

	@Override
	public String toString() {
		return "ApiUserNotificationServiceController [userPushNotificationService=" + this.userPushNotificationService
		        + "]";
	}

}
