package com.dereekb.gae.extras.gen.utility.spring.security.bean;

public interface SpringSecurityXMLHttpBean {

	public static final String ELEMENT = "security:http";

	public static final String REQUEST_MATCHER_REF_ATTRIBUTE = "request-matcher-ref";
	public static final String CREATE_SESSION_ATTRIBUTE = "create-session";
	public static final String ENTRY_POINT_REF_ATTRIBUTE = "entry-point-ref";
	public static final String PATTERN_ATTRIBUTE = "pattern";
	public static final String SECURITY_ATTRIBUTE = "security";

	public static final String ANONYMOUS_ELEMENT = "security:anonymous";
	public static final String ACCESS_DENIED_HANDLER_ELEMENT = "security:access-denied-handler";
	public static final String CSRF_ELEMENT = "security:csrf";

	public static final String USE_EXPRESSIONS = "use-expressions";

}
