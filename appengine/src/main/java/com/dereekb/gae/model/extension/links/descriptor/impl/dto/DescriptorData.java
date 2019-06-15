package com.dereekb.gae.model.extension.links.descriptor.impl.dto;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.links.descriptor.impl.DescriptorImpl;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link Descriptor} implementation used for JSON deserialization.
 *
 * @author dereekb
 *
 */
public class DescriptorData
	implements Descriptor {

	private String descriptorType;
	private String descriptorId;

	public DescriptorData() {}

	public DescriptorData(Descriptor descriptor) {
		if (descriptor != null) {
			this.setDescriptorId(descriptor.getDescriptorId());
			this.setDescriptorType(descriptor.getDescriptorType());
		}
	}

	public static DescriptorData withValue(Descriptor input) {
		DescriptorData descriptor = null;

		if (input != null) {
			descriptor = new DescriptorData(descriptor);
		}

		return descriptor;
	}

	public boolean isValid() {
		return !(StringUtility.isEmptyString(this.descriptorType) && StringUtility.isEmptyString(this.descriptorId));
 	}

	// MARK: Descriptor
	@Override
	public String getDescriptorType() {
		return this.descriptorType;
	}

	public void setDescriptorType(String descriptorType) throws IllegalArgumentException {
		this.descriptorType = descriptorType;
	}

	@Override
	public String getDescriptorId() {
		return this.descriptorId;
	}

	public void setDescriptorId(String descriptorId) throws IllegalArgumentException {
		this.descriptorId = descriptorId;
	}

	@Override
	public boolean equals(Descriptor descriptor) {
		if (this.isValid()) {
			return DescriptorImpl.descriptorsAreEqual(this, descriptor);
		} else {
			return descriptor == null;
		}
	}

	@Override
	public String toString() {
		return "DescriptorData [descriptorType=" + this.descriptorType + ", descriptorId=" + this.descriptorId + "]";
	}

}
