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
import com.dereekb.gae.server.notification.model.shared.NotifiableUniqueModel;
import com.dereekb.gae.server.notification.user.service.UserPushNotificationService;

/**
 * {@link AbstractTransactionModelUpdaterTask} extension for
 * {@link NotifiableUniqueModel} that is typically used for updating the model
 * and then sending notifications, if a notification for this model has not yet
 * been sent.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 *
 * @see AbstractNotifiableStateModelUpdaterTask
 */
public abstract class AbstractNotifiableModelUpdaterTask<T extends NotifiableUniqueModel> extends AbstractModelTransactionUpdateTask<T> {

	private Updater<T> updater;
	private UserPushNotificationService service;

	public AbstractNotifiableModelUpdaterTask(GetterSetter<T> getterSetter, UserPushNotificationService service) {
		this(null, getterSetter, getterSetter, service);
	}

	public AbstractNotifiableModelUpdaterTask(Integer partitionSize,
	        GetterSetter<T> getterSetter,
	        UserPushNotificationService service) {
		this(partitionSize, getterSetter, getterSetter, service);
	}

	public AbstractNotifiableModelUpdaterTask(Getter<T> getter, Setter<T> setter, UserPushNotificationService service) {
		this(null, getter, setter, service);
	}

	public AbstractNotifiableModelUpdaterTask(Integer partitionSize,
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

	// MARK: AbstractNotifiableModelUpdaterTask
	protected abstract class AbstractNotificationTransactionWork extends AbstractModelTransactionUpdaterWork {

		public AbstractNotificationTransactionWork(Iterable<ModelKey> input) {
			super(input);
		}

		@Override
		protected final StagedTransactionChangeCollection updateModels(List<T> models) {
			List<T> modelsToSendNotifications = new ArrayList<T>();

			for (T model : models) {
				if (this.shouldSendNotificationForModel(model)) {
					modelsToSendNotifications.add(model);
					this.setModelAsNotified(model);
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
		 * Whether or not the model should send a notification.
		 * <p>
		 * By default returns {@code false} if notified is true.
		 *
		 * @return {@code true} if a notification for this model should be sent.
		 */
		protected boolean shouldSendNotificationForModel(T model) {
			return model.isNotified() == false;
		}

		/**
		 * Update the model to be notified.
		 */
		protected abstract void setModelAsNotified(T model);

		/**
		 * The updater updates all the models here. Can override to make any
		 * last-second changes.
		 */
		protected void saveUpdatedModels(List<T> models) {
			AbstractNotifiableModelUpdaterTask.this.updater.update(models);
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
				sendNotificationForModel(AbstractNotifiableModelUpdaterTask.this.service, this.model);
			}

		}

		/**
		 * Sends the notification for the target model.
		 *
		 * @param service
		 *            {@link UserPushNotificationService}. Never {@code null}.
		 * @param model
		 *            Model. Never {@code null}.
		 */
		protected abstract void sendNotificationForModel(UserPushNotificationService service,
		                                                 T model);

	}

}
