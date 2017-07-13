package com.dereekb.gae.model.extension.links.system.mutable.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.links.descriptor.impl.DescribedModel;
import com.dereekb.gae.model.extension.links.system.components.LinkSize;
import com.dereekb.gae.model.extension.links.system.components.LinkType;
import com.dereekb.gae.model.extension.links.system.components.exceptions.DynamicLinkInfoException;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkAccessor;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkDataAssertionDelegate;
import com.dereekb.gae.model.extension.links.system.mutable.impl.link.SingleMutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.impl.link.SingleMutableLinkDataDelegate;
import com.dereekb.gae.server.datastore.models.exception.UnknownModelTypeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * {@link AbstractMutableLinkSystemBuilderEntry} extension for {@link DescribedModel} instances.
 * 
 * @author dereekb
 *
 * @param <T> model type
 */
public abstract class AbstractDescriptiveMutableLinkSystemBuilderEntry<T extends DescribedModel> extends AbstractMutableLinkSystemBuilderEntry<T> {

	public static final String DEFAULT_DESCRIPTOR_LINK_NAME = "descriptor";
	
	private String descriptorLinkName = DEFAULT_DESCRIPTOR_LINK_NAME;

	private final DescriptorLinkData descriptorLinkData = new DescriptorLinkData();
	
	private TypeModelKeyConverter typeKeyConverter;
	
	public AbstractDescriptiveMutableLinkSystemBuilderEntry(ReadService<T> readService, TypeModelKeyConverter typeKeyConverter) {
		super(readService);
		this.setTypeKeyConverter(typeKeyConverter);
	}

	protected String getDescriptorLinkName() {
		return this.descriptorLinkName;
	}
	
	protected void setDescriptorLinkName(String descriptorLinkName) {
		if (descriptorLinkName == null) {
			throw new IllegalArgumentException("descriptorLinkName cannot be null.");
		}
	
		this.descriptorLinkName = descriptorLinkName;
	}

	protected TypeModelKeyConverter getTypeKeyConverter() {
		return this.typeKeyConverter;
	}

	protected void setTypeKeyConverter(TypeModelKeyConverter typeKeyConverter) {
		if (typeKeyConverter == null) {
			throw new IllegalArgumentException("typeKeyConverter cannot be null.");
		}
	
		this.typeKeyConverter = typeKeyConverter;
	}
	
	public MutableLinkDataAssertionDelegate<T> getDescriptorLinkAssertionDelegate() {
		return this.descriptorLinkData.getAssertionDelegate();
	}

	public void setDescriptorLinkAssertionDelegate(MutableLinkDataAssertionDelegate<T> assertionDelegate) {
		this.descriptorLinkData.setAssertionDelegate(assertionDelegate);
	}

	// MARK: AbstractMutableLinkSystemBuilderEntry
	@Override
	protected final List<MutableLinkData<T>> makeLinkData() {
		List<MutableLinkData<T>> linkData = new ArrayList<MutableLinkData<T>>();
		
		List<MutableLinkData<T>> definedLinkData = this.makeDefinedLinkData();
		List<MutableLinkData<T>> descriptiveLinkData = this.makeDescriptiveLinkData();
		
		linkData.addAll(definedLinkData);
		linkData.addAll(descriptiveLinkData);
		
		return linkData;
	}

	protected abstract List<MutableLinkData<T>> makeDefinedLinkData();

	protected List<MutableLinkData<T>> makeDescriptiveLinkData() {
		return ListUtility.wrap((MutableLinkData<T>) this.descriptorLinkData);
	}
	
	// MARK: Internal
	/**
	 * Dynamic descriptor link.
	 * 
	 * @author dereekb
	 */
	protected class DescriptorLinkData extends SingleMutableLinkData<T> implements SingleMutableLinkDataDelegate<T> {

		public DescriptorLinkData() {
			super(null, null, null);
			this.setDelegate(this);
		}

		// MARK: SingleMutableLinkData
		@Override
		public String getLinkName() {
			return AbstractDescriptiveMutableLinkSystemBuilderEntry.this.descriptorLinkName;
		}

		@Override
		public LinkType getLinkType() {
			return LinkType.DYNAMIC;
		}

		@Override
		public String getRelationLinkType() throws DynamicLinkInfoException {
			throw new DynamicLinkInfoException();
		}

		@Override
		public LinkSize getLinkSize() {
			return LinkSize.ONE;
		}

		// MARK: SingleMutableLinkDataDelegate
		@Override
		public ModelKey readLinkedModelKey(T model) {
			Descriptor descriptor = model.getDescriptor();
			ModelKey key = null;
			
			if (descriptor != null) {
				String id = descriptor.getDescriptorId();
				String type = descriptor.getDescriptorType();
				
				try {
					key = AbstractDescriptiveMutableLinkSystemBuilderEntry.this.typeKeyConverter.convertKey(type, id);
				} catch (UnknownModelTypeException e) {
					
					// TODO: Consider logging or throwing an exception that the type is unknown...
					
					key = new ModelKey(id);
				}
			}
			
			return key;
		}

		@Override
		public void setLinkedModelKey(T model,
		                              ModelKey key) {
			throw new UnsupportedOperationException("No type information provided.");
		}
		
		@Override
		public MutableLinkAccessor makeLinkAccessor(T model) {
			return new DescriptorAccessor(model);
		}

		// MARK: Accessor
		protected class DescriptorAccessor extends Accessor {

			public DescriptorAccessor(T model) {
				super(model);
			}

			// MARK: DynamicLinkAccessorInfo
			@Override
			public String getRelationLinkType() {
				Descriptor descriptor = this.model.getDescriptor();
				
				if (descriptor != null) {
					return descriptor.getDescriptorType();
				} else {
					return null;
				}
			}

		}
		
	}

	@Override
	public String toString() {
		return "AbstractDescriptiveMutableLinkSystemBuilderEntry [descriptorLinkName=" + this.descriptorLinkName
		        + ", descriptorLinkData=" + this.descriptorLinkData + ", typeKeyConverter=" + this.typeKeyConverter
		        + "]";
	}

}
