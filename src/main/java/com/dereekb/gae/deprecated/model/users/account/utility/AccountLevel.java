package com.thevisitcompany.gae.deprecated.model.users.account.utility;

public enum AccountLevel {

	OWNER(3),

	MEMBER(2),

	VIEWER(1),

	NONE(0);

	private final int level;

	private AccountLevel(int level) {
		this.level = level;
	}

	public boolean below(AccountLevel level) {
		return (this.level < level.level);
	}

	public boolean above(AccountLevel level) {
		return (this.level > level.level);
	}
	
	public boolean has(AccountLevel level) {
		return (this.level >= level.level);
	}

	public int getLevel() {
		return level;
	}

}
