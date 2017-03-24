package com.dereekb.gae.server.auth.model.login.misc.ownership.task;

import com.dereekb.gae.model.crud.task.impl.CreateTaskImpl;
import com.dereekb.gae.model.crud.task.save.IterableUpdateTask;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.misc.ownership.LoginOwnershipRolesReader;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link IterableTask} implementation used for {@link Login} models within a
 * {@link CreateTaskImpl}.
 * 
 * 
 * 
 * @author dereekb
 * @deprecated Use {@link NewLoginOwnershipRolesSaver} for proper saving
 *             instead.
 */
@Deprecated
public class SetLoginOwnershipRolesTask
        implements IterableUpdateTask<Login> {

	private IterableUpdateTask<Login> saveTask;
	private LoginOwnershipRolesReader ownershipRolesReader;

	public SetLoginOwnershipRolesTask(IterableUpdateTask<Login> saveTask,
	        LoginOwnershipRolesReader ownershipRolesReader) {
		this.setSaveTask(saveTask);
		this.setOwnershipRolesReader(ownershipRolesReader);
	}

	public IterableUpdateTask<Login> getSaveTask() {
		return this.saveTask;
	}

	public void setSaveTask(IterableUpdateTask<Login> saveTask) throws IllegalArgumentException {
		if (saveTask == null) {
			throw new IllegalArgumentException("SaveTask cannot be null.");
		}

		this.saveTask = saveTask;
	}

	public LoginOwnershipRolesReader getOwnershipRolesReader() {
		return this.ownershipRolesReader;
	}

	public void setOwnershipRolesReader(LoginOwnershipRolesReader ownershipRolesReader)
	        throws IllegalArgumentException {
		if (ownershipRolesReader == null) {
			throw new IllegalArgumentException("OwnershipRolesReader cannot be null.");
		}

		this.ownershipRolesReader = ownershipRolesReader;
	}

	// MARK: IterableTask
	@Override
	public void doTask(Iterable<Login> input) throws FailedTaskException {
		this.doUpdateTask(input);
	}

	// MARK: IterableSaveTask
	@Override
	public void doUpdateTask(Iterable<Login> input) throws FailedTaskException {
		this.doUpdateTask(input, true);
	}

	@Override
	public void doUpdateTask(Iterable<Login> input,
	                       boolean async)
	        throws FailedTaskException {
		this.saveTask.doUpdateTask(input, false);

		for (Login login : input) {
			String ownerId = this.ownershipRolesReader.makeOwnerId(login);
			login.setOwnerId(ownerId);
		}

		this.saveTask.doUpdateTask(input, async);
	}

	@Override
	public String toString() {
		return "SetLoginOwnershipRolesTask [saveTask=" + this.saveTask + ", ownershipRolesReader="
		        + this.ownershipRolesReader + "]";
	}

}
