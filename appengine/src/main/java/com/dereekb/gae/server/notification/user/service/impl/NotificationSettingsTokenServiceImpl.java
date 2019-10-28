package com.dereekb.gae.server.notification.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.helpers.ObjectifyTransactionUtility;
import com.dereekb.gae.server.notification.model.token.NotificationSettings;
import com.dereekb.gae.server.notification.model.token.NotificationToken;
import com.dereekb.gae.server.notification.service.PushNotificationDevice;
import com.dereekb.gae.server.notification.service.PushNotificationToken;
import com.dereekb.gae.server.notification.user.service.UserPushNotificationTokenAccessor;
import com.dereekb.gae.server.notification.user.service.UserPushNotificationTokenService;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.time.DateUtility;
import com.googlecode.objectify.Work;

/**
 * {@link UserPushNotificationTokenService} implementation that uses
 * {@link NotificationSettings}.
 *
 * @author dereekb
 *
 */
public class NotificationSettingsTokenServiceImpl
        implements UserPushNotificationTokenService {

	private static final Logger LOGGER = Logger.getLogger(NotificationSettingsTokenServiceImpl.class.getName());

	public static final Long DEFAULT_MINIMUM_TOKEN_UPDATE_TIME = DateUtility.timeInDays(2);
	public static final Integer DEFAULT_MAXIMUM_DEVICES_ALLOWED = 6;

	private ObjectifyRegistry<NotificationSettings> notificationSettingsRegistry;
	private Long minimumTokenUpdateTime = DEFAULT_MINIMUM_TOKEN_UPDATE_TIME;
	private Integer maximumDevicesAllowed = DEFAULT_MAXIMUM_DEVICES_ALLOWED;

	public NotificationSettingsTokenServiceImpl(ObjectifyRegistry<NotificationSettings> notificationSettingsRegistry) {
		super();
		this.setNotificationSettingsRegistry(notificationSettingsRegistry);
	}

	public ObjectifyRegistry<NotificationSettings> getNotificationSettingsRegistry() {
		return this.notificationSettingsRegistry;
	}

	public void setNotificationSettingsRegistry(ObjectifyRegistry<NotificationSettings> notificationSettingsRegistry) {
		if (notificationSettingsRegistry == null) {
			throw new IllegalArgumentException("notificationSettingsRegistry cannot be null.");
		}

		this.notificationSettingsRegistry = notificationSettingsRegistry;
	}

	public Long getMinimumTokenUpdateTime() {
		return this.minimumTokenUpdateTime;
	}

	public void setMinimumTokenUpdateTime(Long minimumTokenUpdateTime) {
		if (minimumTokenUpdateTime == null) {
			throw new IllegalArgumentException("minimumTokenUpdateTime cannot be null.");
		}

		this.minimumTokenUpdateTime = minimumTokenUpdateTime;
	}

	public Integer getMaximumDevicesAllowed() {
		return this.maximumDevicesAllowed;
	}

	public void setMaximumDevicesAllowed(Integer maximumDevicesAllowed) {
		if (maximumDevicesAllowed == null) {
			throw new IllegalArgumentException("maximumDevicesAllowed cannot be null.");
		}

		this.maximumDevicesAllowed = maximumDevicesAllowed;
	}

	// MARK: UserPushNotificationTokenService
	@Override
	public NotificationSettingsTokenAccessorImpl makeTokenAccessor(ModelKey userKey) {
		return new NotificationSettingsTokenAccessorImpl(userKey);
	}

	/**
	 * {@link UserPushNotificationTokenAccessor} implementation.
	 *
	 * @author dereekb
	 */
	public class NotificationSettingsTokenAccessorImpl
	        implements UserPushNotificationTokenAccessor {

		private final ModelKey userKey;

		private transient boolean initialized = false;

		private transient NotificationSettings notificationSettings;

		public NotificationSettingsTokenAccessorImpl(ModelKey userKey) {
			super();
			this.userKey = userKey;
		}

		// MARK: Devices
		public void addDevice(PushNotificationDevice device) {
			this.addDevices(ListUtility.wrap(device));
		}

		public void addDevices(final List<PushNotificationDevice> devices) {

			// No changes if empty
			if (devices.isEmpty()) {
				return;
			}

			// Update within a transaction
			this.updateNotificationSettings(new Work<NotificationSettings>() {

				@Override
				public NotificationSettings run() {
					NotificationSettings settings = NotificationSettingsTokenServiceImpl.this.notificationSettingsRegistry
					        .get(NotificationSettingsTokenAccessorImpl.this.userKey);

					boolean isNew = false;

					// If no settings exist for this user, create it.
					if (settings == null) {
						settings = new NotificationSettings(NotificationSettingsTokenAccessorImpl.this.userKey.getId());
						isNew = true;
					}

					boolean wasUpdated = false;

					List<NotificationToken> currentTokens = settings.getTokens();
					Map<String, NotificationToken> currentTokensDeviceMap = new HashMap<String, NotificationToken>();

					for (NotificationToken token : currentTokens) {
						currentTokensDeviceMap.put(token.getDevice(), token);
					}

					for (PushNotificationDevice device : devices) {
						String deviceId = device.getDevice();

						boolean replaceToken = false;

						NotificationToken existingToken = currentTokensDeviceMap.get(deviceId);

						if (existingToken != null) {
							String currentNotificationToken = existingToken.getNotificationToken();
							Date lastUpdateTime = existingToken.getDate();

							// Replace if the token is default or the token
							// hasn't been update in a while.
							if (!currentNotificationToken.equals(device.getNotificationToken())
							        || DateUtility.timeHasPassed(lastUpdateTime,
							                NotificationSettingsTokenServiceImpl.this.minimumTokenUpdateTime)) {
								currentTokensDeviceMap.remove(deviceId);
								replaceToken = true;
							}
						} else {
							replaceToken = true;
						}

						if (replaceToken) {
							NotificationToken token = new NotificationToken(device);
							currentTokensDeviceMap.put(deviceId, token);
							wasUpdated = true;
						}
					}

					if (wasUpdated) {
						List<NotificationToken> newTokens = ListUtility.copy(currentTokensDeviceMap.values());

						if (newTokens.size() >= NotificationSettingsTokenServiceImpl.this.maximumDevicesAllowed) {
							newTokens = DateUtility.sortDatedModelsDescendingOrder(newTokens);
							newTokens = newTokens.subList(0, NotificationSettingsTokenServiceImpl.this.maximumDevicesAllowed);
						}

						settings.setTokens(newTokens);
					}

					if (isNew) {
						NotificationSettingsTokenServiceImpl.this.notificationSettingsRegistry.store(settings);
					} else if (wasUpdated) {
						NotificationSettingsTokenServiceImpl.this.notificationSettingsRegistry.update(settings);
					}

					return settings;
				}

			}, true);
		}

		// MARK: UserPushNotificationTokenAccessor
		@Override
		public ModelKey getUserKey() {
			return this.userKey;
		}

		@Override
		public List<PushNotificationToken> getUserPushNotificationTokens() {
			this.initialize();
			return new ArrayList<PushNotificationToken>(this.notificationSettings.getTokens());
		}

		@Override
		public boolean removeUserPushNotificationTokens(final List<PushNotificationToken> tokensToRemove) {
			boolean success = true;

			if (tokensToRemove.isEmpty()) {
				return success;
			}

			// Update within a transaction
			success = this.updateNotificationSettings(new Work<NotificationSettings>() {

				@Override
				public NotificationSettings run() {
					NotificationSettings settings = NotificationSettingsTokenServiceImpl.this.notificationSettingsRegistry
					        .get(NotificationSettingsTokenAccessorImpl.this.userKey);

					// If no settings exist for this user, do
					// nothing.
					if (settings == null) {
						return settings;
					}

					Set<String> tokenStringsToRemove = new HashSet<String>();

					for (PushNotificationToken token : tokensToRemove) {
						tokenStringsToRemove.add(token.getNotificationToken());
					}

					List<NotificationToken> currentTokens = settings.getTokens();
					List<NotificationToken> newTokens = new ArrayList<NotificationToken>();

					for (NotificationToken token : currentTokens) {
						boolean shouldRemove = tokenStringsToRemove.contains(token.getNotificationToken());

						if (!shouldRemove) {
							newTokens.add(token);
						}
					}

					settings.setTokens(newTokens);

					if (currentTokens.size() != newTokens.size()) {
						NotificationSettingsTokenServiceImpl.this.notificationSettingsRegistry.update(settings);
					}

					return settings;
				}

			}, false);

			return success;
		}

		// MARK: Internal
		private void initialize() {
			if (!this.initialized) {
				this.notificationSettings = NotificationSettingsTokenServiceImpl.this.notificationSettingsRegistry
				        .get(this.userKey);

				if (this.notificationSettings == null) {
					this.notificationSettings = new NotificationSettings(this.userKey.getId());
				}

				this.initialized = true;
			}
		}

		private boolean updateNotificationSettings(Work<NotificationSettings> updateWork,
		                                           boolean required) {
			boolean success = true;

			try {
				this.notificationSettings = ObjectifyTransactionUtility.newTransact(2).doTransaction(updateWork);
				this.initialized = true;
			} catch (RuntimeException e) {
				if (required) {
					throw e;
				}

				LOGGER.log(Level.WARNING, "Failed to removed tokens from notification settings.", e);
				success = false;
			}

			return success;
		}

	}

}
