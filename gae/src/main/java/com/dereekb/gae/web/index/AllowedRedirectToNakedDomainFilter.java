package com.dereekb.gae.web.index;

import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.utilities.collections.set.CaseInsensitiveSet;

/**
 * {@link RedirectToNakedDomainFilter} that can allow certain sub domains.
 * 
 * @author dereekb
 *
 */
public class AllowedRedirectToNakedDomainFilter extends RedirectToNakedDomainFilter {

	private Set<String> allowedSubDomains = null;

	public AllowedRedirectToNakedDomainFilter(String redirectUrl, Set<String> allowed) {
		super(redirectUrl);
		this.setAllowedSubDomains(allowed);
	}

	public Set<String> getAllowedSubDomains() {
		return this.allowedSubDomains;
	}

	public void setAllowedSubDomains(Collection<String> allowedSubDomains) {
		if (allowedSubDomains == null) {
			throw new IllegalArgumentException("allowedSubDomains cannot be null.");
		}

		this.allowedSubDomains = new CaseInsensitiveSet(allowedSubDomains);
	}

	// MARK: RedirectToNakedDomainFilter
	@Override
	protected boolean shouldRedirect(HttpServletRequest req) {
		String subDomain = getSubDomain(req);

		if (subDomain != null) {
			return this.allowedSubDomains.contains(subDomain) == false;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "AllowedRedirectToNakedDomainFilter [allowedSubDomains=" + this.allowedSubDomains + "]";
	}

}
