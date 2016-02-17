package com.dereekb.gae.utilities.misc.path.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dereekb.gae.utilities.misc.path.PathUtility;
import com.dereekb.gae.utilities.misc.path.SimplePath;

/**
 * {@link SimplePath} implementation.
 *
 * @author dereekb
 *
 */
public class SimplePathImpl
        implements SimplePath {

	public static final String DEFAULT_DIVIDER = "/";

	private final String divider;
	private final List<String> pathComponents;

	private String path; // Lazy Init

	public SimplePathImpl() {
		this.pathComponents = Collections.emptyList();
		this.divider = DEFAULT_DIVIDER;
	}

	public SimplePathImpl(String path) {
		this(PathUtility.getComponents(path, DEFAULT_DIVIDER));
	}

	public SimplePathImpl(String path, String divider) {
		this(PathUtility.getComponents(path, divider), divider);
	}

	public SimplePathImpl(List<String> pathComponents) {
		this(pathComponents, null);
	}

	public SimplePathImpl(List<String> pathComponents, String divider) {
		if (divider == null) {
			divider = DEFAULT_DIVIDER;
		}

		if (pathComponents == null) {
			throw new IllegalArgumentException("Components cannot be null.");
		}

		this.pathComponents = pathComponents;
		this.divider = divider;
	}

	public SimplePathImpl(SimplePath path) {
		this(path, null);
	}

	public SimplePathImpl(SimplePath path, String divider) {
		this(path.getPathComponents(), divider);
	}

	public SimplePathImpl(SimplePath... paths) {
		this(PathUtility.mergePaths(paths));
	}

	public static SimplePathImpl withPaths(String divider,
	                                       SimplePath... paths) {
		return new SimplePathImpl(PathUtility.mergePaths(paths), divider);
	}

	public static SimplePathImpl withPaths(SimplePath... paths) {
		return withPaths(null, paths);
	}

	public String getDivider() {
		return this.divider;
	}

	@Override
	public String getPath() {
		if (this.path == null) {
			this.path = this.buildPath();
		}

		return this.divider + this.path;
	}

	private String buildPath() {
		return PathUtility.buildPath(this.pathComponents, this.divider).toLowerCase();
	}

	// MARK: Simple Path
	@Override
	public List<String> getPathComponents() {
		return new ArrayList<String>(this.pathComponents);
	}

	@Override
	public SimplePathImpl append(String path) {
		List<String> pathComponents = PathUtility.getComponents(path, this.divider);
		List<String> merged = PathUtility.mergeComponents(this.pathComponents, pathComponents);
		return new SimplePathImpl(merged, this.divider);
	}

	@Override
	public SimplePathImpl append(SimplePath path) {
		List<String> pathComponents = path.getPathComponents();
		List<String> merged = PathUtility.mergeComponents(this.pathComponents, pathComponents);
		return new SimplePathImpl(merged, this.divider);
	}

	@Override
    public int hashCode() {
		return this.getPath().hashCode();
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
        }
	    if (obj == null) {
	        return false;
        }
	    if (this.getClass() != obj.getClass()) {
	        return false;
        }
		SimplePath other = (SimplePath) obj;
		if (this.getPath() == null) {
			if (other.getPath() != null) {
	            return false;
            }
		} else if (!this.getPath().equals(other.getPath())) {
	        return false;
        }
	    return true;
    }

	@Override
	public String toString() {
		return "SimplePathImpl [divider=" + this.divider + ", pathComponents=" + this.pathComponents + ", getPath()="
		        + this.getPath() + "]";
	}

}
