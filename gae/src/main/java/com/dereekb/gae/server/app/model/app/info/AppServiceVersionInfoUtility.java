package com.dereekb.gae.server.app.model.app.info;

import java.util.Comparator;

import com.dereekb.gae.server.app.model.app.info.exception.AppIdInequalityException;
import com.dereekb.gae.server.app.model.app.info.exception.AppInequalityException;
import com.dereekb.gae.server.app.model.app.info.exception.AppMajorVersionInequalityException;
import com.dereekb.gae.server.app.model.app.info.exception.AppServiceInequalityException;

public class AppServiceVersionInfoUtility {

	/**
	 * Asserts the apps are equivalent in app, major versions and service names.
	 *
	 * @param a
	 * @param b
	 * @throws AppMajorVersionInequalityException
	 * @throws AppServiceInequalityException
	 * @throws AppInequalityException
	 */
	public static void assertEquivalentApp(AppInfo a,
	                                       AppInfo b)
	        throws AppMajorVersionInequalityException,
	            AppServiceInequalityException,
	            AppIdInequalityException,
	            AppInequalityException {
		if (isEquivalentAppService(a, b) == false) {
			throw new AppMajorVersionInequalityException();
		} else if (isEquivalentAppMajorVersion(a, b) == false) {
			throw new AppServiceInequalityException();
		} else if (isEquivalentAppId(a, b) == false) {
			throw new AppServiceInequalityException();
		}
	}

	public static boolean isEquivalentAppId(AppInfo a,
	                                             AppInfo b)
	        throws AppServiceInequalityException {

		String aService = a.getAppServiceVersionInfo().getAppId();
		String bService = b.getAppServiceVersionInfo().getAppId();
		return aService.equals(bService);
	}

	public static boolean isEquivalentAppService(AppInfo a,
	                                             AppInfo b)
	        throws AppServiceInequalityException {

		String aService = a.getAppServiceVersionInfo().getAppService();
		String bService = b.getAppServiceVersionInfo().getAppService();
		return aService.equals(bService);
	}

	public static boolean isEquivalentAppMajorVersion(AppInfo a,
	                                                  AppInfo b)
	        throws AppServiceInequalityException {
		return (new AppInfoMajorVersionComparator().compare(a, b) == 0);
	}

	public static class AppInfoMajorVersionComparator
	        implements Comparator<AppInfo> {

		@Override
		public int compare(AppInfo o1,
		                   AppInfo o2) {
			String ov1 = o1.getAppServiceVersionInfo().getAppVersion().getMajorVersion();
			String ov2 = o2.getAppServiceVersionInfo().getAppVersion().getMajorVersion();
			return ov1.compareTo(ov2);
		}

	}

}
