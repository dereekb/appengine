package com.dereekb.gae.server.app.model.app.info.impl;

import com.dereekb.gae.server.app.model.app.info.AppVersion;

/**
 * {@link AppVersion} implementation.
 *
 * @author dereekb
 *
 */
public class AppVersionImpl
        implements AppVersion {

	private String majorVersion;
	private String minorVersion;

	public AppVersionImpl(String majorVersion, String minorVersion) {
		this.setMajorVersion(majorVersion);
		this.setMinorVersion(minorVersion);
	}

	@Override
	public String getMajorVersion() {
		return this.majorVersion;
	}

	public void setMajorVersion(String majorVersion) {
		if (majorVersion == null) {
			throw new IllegalArgumentException("majorVersion cannot be null.");
		}

		this.majorVersion = majorVersion;
	}

	@Override
	public String getMinorVersion() {
		return this.minorVersion;
	}

	public void setMinorVersion(String minorVersion) {
		if (minorVersion == null) {
			throw new IllegalArgumentException("minorVersion cannot be null.");
		}

		this.minorVersion = minorVersion;
	}

	@Override
	public String toString() {
		return "AppVersionImpl [majorVersion=" + this.majorVersion + ", minorVersion=" + this.minorVersion + "]";
	}

}
