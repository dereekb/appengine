package com.thevisitcompany.gae.deprecated.model.users.account.functions;

import com.thevisitcompany.gae.deprecated.model.users.account.Account;
import com.thevisitcompany.gae.model.extension.links.functions.LinksAction;
import com.thevisitcompany.gae.model.extension.links.functions.LinksMethod;
import com.thevisitcompany.gae.model.extension.links.functions.LinksPair;
import com.thevisitcompany.gae.model.extension.links.functions.filters.CanLinkFilterDelegate;

public class AccountLinksFunctionDelegate
        implements CanLinkFilterDelegate<Account> {

	@Override
	public boolean canLink(Account object,
	                       String link,
	                       LinksAction action) {
		// TODO Auto-generated method stub
		return false;
	}

	@LinksMethod("owner")
	public void linkPlace(LinksPair<Account, Long> pair) {
		// TODO:
	}

	@LinksMethod(value = "owner", action = LinksAction.UNLINK)
	public void unlinkPlace(LinksPair<Account, Long> pair) {
		// TODO:
	}

	@LinksMethod("member")
	public void linkAccount(LinksPair<Account, Long> pair) {
		// TODO:
	}

	@LinksMethod(value = "member", action = LinksAction.UNLINK)
	public void unlinkAccount(LinksPair<Account, Long> pair) {
		// TODO:
	}

}
