package com.dereekb.gae.server.notification.user.service;

/**
 * {@link UserPushNotificationService} for sending messages to specific users
 * and registering devices.
 *
 * @author dereekb
 *
 */
public interface UserPushNotificationService
        extends UserPushNotificationDeviceService, UserPushNotificationSendService {}
