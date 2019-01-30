package com.dereekb.gae.model.extension.links.descriptor.impl;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;

/**
 * {@link Descriptor} implementation.
 *
 * @author dereekb
 *
 */
public class DescriptorImpl
        implements Descriptor {

	private String descriptorType;
	private String descriptorId;

	public DescriptorImpl(String descriptorType, String descriptorId) {
		this.setDescriptorType(descriptorType);
		this.setDescriptorId(descriptorId);
	}

	public DescriptorImpl(Descriptor descriptor) {
		this.setDescriptorType(descriptor.getDescriptorType());
		this.setDescriptorId(descriptor.getDescriptorId());
	}

	@Override
	public String getDescriptorType() {
		return this.descriptorType;
	}

	public void setDescriptorType(String descriptorType) throws IllegalArgumentException {
		if (descriptorType == null) {
			throw new IllegalArgumentException("Descriptor type cannot be null.");
		}

		this.descriptorType = descriptorType;
	}

	@Override
	public String getDescriptorId() {
		return this.descriptorId;
	}

	public void setDescriptorId(String descriptorId) throws IllegalArgumentException {
		if (descriptorId == null) {
			throw new IllegalArgumentException("Descriptor id cannot be null.");
		}

		this.descriptorId = descriptorId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.descriptorId == null) ? 0 : this.descriptorId.hashCode());
		result = prime * result + ((this.descriptorType == null) ? 0 : this.descriptorType.hashCode());
		return result;
	}

	@Override
    public boolean equals(Descriptor obj) {
		return descriptorsAreEqual(this, obj);
	}

	/**
	 * Compares two descriptors. Can input {@code null} values.
	 *
	 * @param a
	 *            {@link Descriptor}
	 * @param b
	 *            {@link Descriptor}
	 * @return {@code true} if both descriptors reference the same object.
	 */
	public static boolean descriptorsAreEqual(Descriptor a,
	                                          Descriptor b) {
		if (a == b) {
			return true;
		}

		if (a == null || b == null) {
			return false;
		}

		String descriptorAType = a.getDescriptorType();
		String descriptorAId = a.getDescriptorId();

		String descriptorBType = b.getDescriptorType();
		String descriptorBId = b.getDescriptorId();

		if (descriptorAType.equals(descriptorBType) == false) {
			return false;
		}

		if (descriptorAId.equals(descriptorBId) == false) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "DescriptorImpl [descriptorType=" + this.descriptorType + ", descriptorId=" + this.descriptorId + "]";
	}

	/**
	 * Creates a new {@link DescriptorImpl} from the input {@link Descriptor}.
	 * Returns {@code null} if the input is {@code null}.
	 *
	 * @param input
	 *            Input {@link Descriptor}. Can be null.
	 * @return A new {@link DescriptorImpl} made with the input
	 *         {@link Descriptor}, or {@code null}.
	 */
	public static DescriptorImpl withValue(Descriptor input) {
		DescriptorImpl descriptor = null;

		if (input != null) {
			descriptor = new DescriptorImpl(input);
		}

		return descriptor;
	}

	public static DescriptorImpl withValues(String type,
	                                        String id) {
		DescriptorImpl descriptor;

		try {
			descriptor = new DescriptorImpl(type, id);
		} catch (IllegalArgumentException e) {
			descriptor = null;
		}

		return descriptor;
	}

}
