package com.dereekb.gae.server.notification.model.shared.taskqueue;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.taskqueue.updater.task.AbstractTransactionModelUpdaterTask;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.Setter;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.task.impl.AbstractModelTransactionUpdateTask;
import com.dereekb.gae.server.datastore.utility.StagedTransactionAlreadyFinishedException;
import com.dereekb.gae.server.datastore.utility.StagedTransactionChange;
import com.dereekb.gae.server.datastore.utility.impl.StagedTransactionChangeCollection;
import com.dereekb.gae.server.notification.model.shared.NotifiableStateCode;
import com.dereekb.gae.server.notification.model.shared.NotifiableStateUniqueModel;
import com.dereekb.gae.server.notification.user.service.UserPushNotificationService;

/**
 * {@link AbstractTransactionModelUpdaterTask} extension for
 * {@link NotifiableStateUniqueModel} that is typically used for updating the
 * model and then sending notifications, if a notification for this model has
 * not yet been sent.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractNotifiableStateModelUpdaterTask<T extends NotifiableStateUniqueModel<S>, S extends NotifiableStateCode> extends AbstractModelTransactionUpdateTask<T> {

	private Updater<T> updater;
	private UserPushNotificationService service;

	public AbstractNotifiableStateModelUpdaterTask(GetterSetter<T> getterSetter, UserPushNotificationService service) {
		this(null, getterSetter, getterSetter, service);
	}

	public AbstractNotifiableStateModelUpdaterTask(Integer partitionSize,
	        GetterSetter<T> getterSetter,
	        UserPushNotificationService service) {
		this(partitionSize, getterSetter, getterSetter, service);
	}

	public AbstractNotifiableStateModelUpdaterTask(Getter<T> getter,
	        Setter<T> setter,
	        UserPushNotificationService service) {
		this(null, getter, setter, service);
	}

	public AbstractNotifiableStateModelUpdaterTask(Integer partitionSize,
	        Getter<T> getter,
	        Setter<T> setter,
	        UserPushNotificationService service) {
		super(partitionSize, getter);
		this.setService(service);
		this.setUpdater(setter);
	}

	public UserPushNotificationService getService() {
		return this.service;
	}

	public void setService(UserPushNotificationService service) {
		if (service == null) {
			throw new IllegalArgumentException("service cannot be null.");
		}

		this.service = service;
	}

	public Updater<T> getUpdater() {
		return this.updater;
	}

	public void setUpdater(Updater<T> updater) {
		if (updater == null) {
			throw new IllegalArgumentException("updater cannot be null.");
		}

		this.updater = updater;
	}

	// MARK: AbstractNotifiableStateModelUpdaterTask
	protected abstract class AbstractNotificationStateTransactionWork extends AbstractModelTransactionUpdaterWork {

		public AbstractNotificationStateTransactionWork(Iterable<ModelKey> input) {
			super(input);
		}

		@Override
		protected final StagedTransactionChangeCollection updateModels(List<T> models) {
			List<T> modelsToSendNotifications = new ArrayList<T>();

			for (T model : models) {
				S nextNotificationState = this.getNextNotificationStateCode(model);

				if (nextNotificationState != null
				        && this.shouldSendNotificationForModel(model, nextNotificationState)) {
					modelsToSendNotifications.add(model);
					this.setModelAsNotified(model, nextNotificationState);
				}
			}

			// Return if nothing to update
			if (modelsToSendNotifications.isEmpty()) {
				return null;
			}

			StagedTransactionChangeCollection collection = super.updateModels(modelsToSendNotifications);

			this.saveUpdatedModels(models);

			return collection;
		}

		/**
		 * Returns the next notification state code for the model, if it can be
		 * determined.
		 *
		 * @return {@link NotificationStateCode}, or {@code null} if the next
		 *         state could not be determined.
		 */
		protected abstract S getNextNotificationStateCode(T model);

		/**
		 * Whether or not the model should send a notification.
		 * <p>
		 * By default returns {@code false} if the current state codes are the
		 * same.
		 *
		 * @param model
		 *            Model. Never {@code null}.
		 * @param nextNotificationState
		 *            Next notification state. Never {@code null}.
		 * @return {@code true} if a notification for this model should be sent.
		 */
		protected boolean shouldSendNotificationForModel(T model,
		                                                 S nextNotificationState) {
			return model.getNotifiableState().getStateCode() != nextNotificationState.getStateCode();
		}

		/**
		 * Updates the model's notification state.
		 */
		protected void setModelAsNotified(T model,
		                                  S newNotificationState) {
			model.setNotifiableState(newNotificationState);
		}

		/**
		 * The updater updates all the models here. Can override to make any
		 * last-second changes.
		 */
		protected void saveUpdatedModels(List<T> models) {
			AbstractNotifiableStateModelUpdaterTask.this.updater.update(models);
		}

		@Override
		protected final StagedTransactionChange updateModel(T model) {
			return new SendNotificationStagedTransactionChange(model);
		}

		private final class SendNotificationStagedTransactionChange
		        implements StagedTransactionChange {

			private final T model;

			protected SendNotificationStagedTransactionChange(T model) {
				super();
				this.model = model;
			}

			@Override
			public void finishChanges() throws StagedTransactionAlreadyFinishedException {
				S notificationState = this.model.getNotifiableState();
				sendNotificationForModel(AbstractNotifiableStateModelUpdaterTask.this.service, this.model,
				        notificationState);
			}

		}

		/**
		 * Sends the target notification for the target model.
		 *
		 * @param service
		 *            {@link UserPushNotificationService}. Never {@code null}.
		 * @param model
		 *            Model. Never {@code null}.
		 * @param notificationState
		 *            Notification State. Never {@code null}.
		 */
		protected abstract void sendNotificationForModel(UserPushNotificationService service,
		                                                 T model,
		                                                 S notificationState);

	}

}
