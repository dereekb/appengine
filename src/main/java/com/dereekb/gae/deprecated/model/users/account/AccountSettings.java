package com.thevisitcompany.gae.deprecated.model.users.account;

import java.io.Serializable;



/**
 * Settings for a given account.
 * 
 * Settings include:
 * - Whether or not members can create places. If false, only owners can create places.
 * 
 * @author dereekb
 */

public class AccountSettings implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean membersCanCreate = false;
	
	public AccountSettings() {}

	public Boolean getMembersCanCreate() {
		return membersCanCreate;
	}

	public void setMembersCanCreate(Boolean membersCanCreate) {
		this.membersCanCreate = membersCanCreate;
	};

}
