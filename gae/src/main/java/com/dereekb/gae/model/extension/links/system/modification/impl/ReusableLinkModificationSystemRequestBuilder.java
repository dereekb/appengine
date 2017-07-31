package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.utilities.collections.list.SetUtility.SetInfo;

/**
 * Used for building a reusable {@link LinkModificationSystemRequest}.
 * <p>
 * This is useful for instances where multiple primary models need to have
 * roughly the same request, or need to clear multiple links, etc.
 * 
 * @author dereekb
 *
 */
public class ReusableLinkModificationSystemRequestBuilder {

	private static final Set<RequestConfigPart> FULL_SET = SetUtility.makeSet(RequestConfigPart.LINK_NAME,
	        RequestConfigPart.LINK_MODEL_TYPE, RequestConfigPart.LINK_CHANGE_TYPE, RequestConfigPart.LINK_NAME);

	public static RequestBuilderItem make(String linkModelType) {
		return new RootItem(linkModelType).makeChild();
	}

	public static class IncompleteInstanceException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		private Set<RequestConfigPart> missing;

		public IncompleteInstanceException(Set<RequestConfigPart> missing) {
			this.setMissing(missing);
		}

		public Set<RequestConfigPart> getMissing() {
			return this.missing;
		}

		public void setMissing(Set<RequestConfigPart> missing) {
			if (missing == null) {
				throw new IllegalArgumentException("missing cannot be null.");
			}

			this.missing = missing;
		}

	}

	// MARK: Enum
	public static enum RequestConfigPart {

		PRIMARY_KEY,

		LINK_NAME,

		LINK_MODEL_TYPE,

		LINK_CHANGE_TYPE,

	    // Optional for CLEAR
		KEYS,

	    // Optional
		DYNAMIC_LINK_MODEL_TYPE

	}

	// MARK: Instances
	/**
	 * Generic {@link ReusableLinkModificationSystemRequestBuilder} instance.
	 * 
	 * @author dereekb
	 *
	 */
	public static interface RequestBuilderInstance {

		/**
		 * Whether or not the instance is fully configured.
		 * 
		 * @return {@code true} if this is fully configured.
		 */
		public boolean isFullRequest();

		/**
		 * Returns the set of configuration parts.
		 * 
		 * @return {@link Set}. Never {@code null}.
		 */
		public Set<RequestConfigPart> getConfigParts();

		// MARK: Basic Components
		/**
		 * 
		 * @return {@link String}. Never {@code null}.
		 */
		public String getLinkModelType();

		/**
		 * 
		 * @return {@link MutableLinkChangeType}, or {@code null} if not yet
		 *         configured.
		 */
		public MutableLinkChangeType getLinkChangeType();

	}

	public static interface RequestBuilderItem
	        extends RequestBuilderInstance {
		
		// MARK: Accessors
		public void setRequestKey(ModelKey requestKey);

		public String getLinkName();

		public void setLinkName(String linkName);
		
		public String getPrimaryKey();
		
		public void setPrimaryKey(String primaryKey);
		
		@Override
		public MutableLinkChangeType getLinkChangeType();
		
		public void setLinkChangeType(MutableLinkChangeType linkChangeType);
		
		public String getDynamicLinkModelType();

		public void setDynamicLinkModelType(String dynamicLinkModelType);
		
		public Set<String> getKeys();
		
		public void setKeys(Collection<String> keys);

		// MARK: Building
		/**
		 * Creates a new group for all input primary keys.
		 * 
		 * @return {@link RequestBuilderGroup}. Never {@code null}.
		 */
		public RequestBuilderGroup makeForPrimaryKeys(Collection<String> primaryKeys);

		/**
		 * Creates a new group for the input link names.
		 * 
		 * @param linkNames
		 *            {@link Collection}. Never {@code null}.
		 * 
		 * @return {@link RequestBuilderGroup}. Never {@code null}.
		 * 
		 * @throws UnsupportedOperationException
		 *             Thrown if {@link #getLinkChangeType()} does not return
		 *             {@link MutableLinkChangeType#CLEAR}.
		 */
		public RequestBuilderGroup makeForLinkNames(Collection<String> linkNames) throws UnsupportedOperationException;

	}

	public static interface RequestBuilderGroup
	        extends RequestBuilderItem {

		/**
		 * Expands this group into multiple new builders.
		 * 
		 * @return {@link List}. Never {@code null}.
		 */
		public List<? extends RequestBuilderItem> expand();

		/**
		 * Flattens the group and creates requests.
		 * 
		 * @return {@link List}. Never {@code null}.
		 * @throws IncompleteInstanceException
		 *             thrown if the builder isn't fully configured.
		 */
		public List<LinkModificationSystemRequest> makeRequests() throws IncompleteInstanceException;

	}

	private static abstract class AbstractInstance
	        implements RequestBuilderInstance {

		@Override
		public boolean isFullRequest() {
			return this.getRequiredMissingParts().isEmpty();
		}

		protected Set<RequestConfigPart> getRequiredMissingParts() {
			Set<RequestConfigPart> missing = this.getMissingParts();

			if (missing.isEmpty() == false) {

				// Remove KEYS from missing if it is a CLEAR request.
				if (missing.contains(RequestConfigPart.KEYS)) {
					MutableLinkChangeType changeType = this.getLinkChangeType();

					if (changeType == MutableLinkChangeType.CLEAR) {
						missing.remove(RequestConfigPart.KEYS);
					}
				}
			}

			return missing;
		}

		protected Set<RequestConfigPart> getMissingParts() {
			Set<RequestConfigPart> parts = this.getConfigParts();

			SetInfo<RequestConfigPart> info = SetUtility.makeSetInfo(FULL_SET, parts);

			Set<RequestConfigPart> missing = info.getUnique();

			return missing;
		}

	}

	// MARK: Items
	private static abstract class AbstractItem extends AbstractInstance
	        implements LinkModificationSystemRequest, RequestBuilderItem {

		/**
		 * @deprecated This will probably never be called without throwing an
		 *             exception, as the requests will build AbstractGroup
		 *             instances.
		 */
		@SuppressWarnings("unused")
		@Deprecated
		public LinkModificationSystemRequest makeRequest() {
			Set<RequestConfigPart> requiredMissing = this.getRequiredMissingParts();

			if (requiredMissing.isEmpty() == false) {
				throw new IncompleteInstanceException(requiredMissing);
			} else {
				return this;
			}
		}

		// MARK: Item
		@Override
		public RequestBuilderGroup makeForPrimaryKeys(Collection<String> primaryKeys) {
			return new PrimaryKeysGroup(this, primaryKeys);
		}

		@Override
		public RequestBuilderGroup makeForLinkNames(Collection<String> linkNames) throws UnsupportedOperationException {
			if (this.getLinkChangeType() != MutableLinkChangeType.CLEAR) {
				throw new UnsupportedOperationException("Change type must be CLEAR.");
			}

			return new LinkNamesGroup(this, linkNames);
		}

	}

	/**
	 * Root internal {@link RootItem}.
	 * 
	 * @author dereekb
	 *
	 */
	private static class RootItem extends AbstractItem {

		private static final Set<RequestConfigPart> ROOT_PARTS = SetUtility.makeSet(RequestConfigPart.LINK_MODEL_TYPE);

		private String linkModelType;

		public RootItem(String linkModelType) {
			this.setLinkModelType(linkModelType);
		}

		@Override
		public String getLinkModelType() {
			return this.linkModelType;
		}

		public void setLinkModelType(String linkModelType) {
			if (linkModelType == null) {
				throw new IllegalArgumentException("linkModelType cannot be null.");
			}

			this.linkModelType = linkModelType;
		}
		
		public ChildItem makeChild() {
			return new ChildItem(this);
		}

		// MARK: Item
		@Override
		public ModelKey keyValue() {
			return null;
		}

		@Override
		public String getLinkName() {
			return null;
		}

		@Override
		public String getPrimaryKey() {
			return null;
		}

		@Override
		public MutableLinkChangeType getLinkChangeType() {
			return null;
		}

		@Override
		public String getDynamicLinkModelType() {
			return null;
		}

		@Override
		public Set<String> getKeys() {
			return null;
		}

		@Override
		public void setRequestKey(ModelKey requestKey) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setLinkName(String linkName) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setPrimaryKey(String primaryKey) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setLinkChangeType(MutableLinkChangeType linkChangeType) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setDynamicLinkModelType(String dynamicLinkModelType) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setKeys(Collection<String> keys) {
			throw new UnsupportedOperationException();
		}

		// MARK: Item
		@Override
		public Set<RequestConfigPart> getConfigParts() {
			return ROOT_PARTS;
		}

		@Override
		public boolean isFullRequest() {
			return false;
		}

	}

	private static class ChildItem extends AbstractItem {

		protected final AbstractItem parent;

		private ModelKey requestKey;
		private String primaryKey;

		private String linkName;
		private MutableLinkChangeType linkChangeType;
		private String dynamicLinkModelType;

		private Set<String> keys;

		public ChildItem(AbstractItem parent) {
			this.parent = parent;
		}

		@Override
		public ModelKey keyValue() {
			return this.requestKey;
		}
		
		@Override
		public void setRequestKey(ModelKey requestKey) {
			this.requestKey = requestKey;
		}

		@Override
		public String getLinkName() {
			return (this.linkName != null) ? this.linkName : this.parent.getLinkName();
		}

		@Override
		public void setLinkName(String linkName) {
			this.linkName = linkName;
		}
		
		@Override
		public String getPrimaryKey() {
			return (this.primaryKey != null) ? this.primaryKey : this.parent.getPrimaryKey();
		}

		@Override
		public void setPrimaryKey(String primaryKey) {
			this.primaryKey = primaryKey;
		}

		@Override
		public MutableLinkChangeType getLinkChangeType() {
			return (this.linkChangeType != null) ? this.linkChangeType : this.parent.getLinkChangeType();
		}

		@Override
		public void setLinkChangeType(MutableLinkChangeType linkChangeType) {
			this.linkChangeType = linkChangeType;
		}

		
		@Override
		public String getDynamicLinkModelType() {
			return (this.dynamicLinkModelType != null) ? this.dynamicLinkModelType
			        : this.parent.getDynamicLinkModelType();
		}

		@Override
		public void setDynamicLinkModelType(String dynamicLinkModelType) {
			this.dynamicLinkModelType = dynamicLinkModelType;
		}
		
		@Override
		public Set<String> getKeys() {
			return (this.keys != null) ? this.keys : this.parent.getKeys();
		}

		@Override
		public void setKeys(Collection<String> keys) {
			this.keys = SetUtility.copy(keys);
		}
		
		@Override
		public String getLinkModelType() {
			return this.parent.getLinkModelType();
		}

		@Override
		public Set<RequestConfigPart> getConfigParts() {
			Set<RequestConfigPart> parts = new HashSet<RequestConfigPart>(this.parent.getConfigParts());

			if (this.linkName != null) {
				parts.add(RequestConfigPart.LINK_NAME);
			}

			if (this.primaryKey != null) {
				parts.add(RequestConfigPart.PRIMARY_KEY);
			}

			if (this.linkChangeType != null) {
				parts.add(RequestConfigPart.LINK_CHANGE_TYPE);
			}

			if (this.dynamicLinkModelType != null) {
				parts.add(RequestConfigPart.DYNAMIC_LINK_MODEL_TYPE);
			}

			if (this.keys != null) {
				parts.add(RequestConfigPart.KEYS);
			}

			// Link model type is always included by the root.

			return parts;
		}

	}

	// MARK: Group
	private static abstract class AbstractGroup extends ChildItem
	        implements RequestBuilderGroup {

		public AbstractGroup(AbstractItem parent) {
			super(parent);
		}

		// MARK: RequestBuilderGroup
		@Override
		public List<? extends AbstractItem> expand() {
			if (this.parent instanceof AbstractGroup) {
				List<? extends AbstractItem> expanded = ((AbstractGroup) this.parent).expand();

				List<AbstractItem> items = new ArrayList<AbstractItem>(expanded.size() * this.getGroupSize()); 
				
				for (AbstractItem expansion : expanded) {
					List<? extends AbstractItem> children = this.createItemsForParent(expansion);
					items.addAll(children);
				}
				
				return items;
			} else {
				return this.createItemsForParent(this);
			}
		}
		
		protected abstract int getGroupSize();

		protected abstract List<? extends AbstractItem> createItemsForParent(AbstractItem parent);
		
		@Override
		public final List<LinkModificationSystemRequest> makeRequests() {
			Set<RequestConfigPart> requiredMissing = this.getRequiredMissingParts();

			if (requiredMissing.isEmpty() == false) {
				throw new IncompleteInstanceException(requiredMissing);
			} else {
				LinkModificationSystemRequest root = this.makeRootRequest();
				return this.makeRequests(root);
			}
		}
		
		private final LinkModificationSystemRequest makeRootRequest() {
			LinkModificationSystemRequestImpl request = new LinkModificationSystemRequestImpl();

			request.setLinkModelType(this.getLinkModelType());
			request.setLinkChangeType(this.getLinkChangeType());
			
			// Optionals
			Set<String> keys = this.getKeys();
			
			if (keys == null) {
				keys = new HashSet<String>();
			}
			
			request.setKeys(keys);
			
			String linkName = this.getLinkName();
			
			if (linkName != null) {
				request.setLinkName(linkName);
			}
			
			String primaryKey = this.getPrimaryKey();
			
			if (primaryKey != null) {
				request.setPrimaryKey(primaryKey);
			}

			request.setDynamicLinkModelType(this.getDynamicLinkModelType());
			
			return request;
		}

		protected List<LinkModificationSystemRequest> makeRequests(LinkModificationSystemRequest rootRequest) {
			List<? extends AbstractItem> items = this.expand();
			List<LinkModificationSystemRequest> requests = new ArrayList<LinkModificationSystemRequest>();
			
			for (RequestBuilderItem item : items) {
				WrappedRequest request = new WrappedRequest(item, rootRequest);
				requests.add(request);
			}
			
			return requests;
		}
		
		private static class WrappedRequest extends AbstractWrappedLinkModificationSystemRequest {
			
			private RequestBuilderItem item;
			
			public WrappedRequest(RequestBuilderItem item, LinkModificationSystemRequest request) {
				super(request);
				this.item = item;
			}

			@Override
			public String getLinkName() {
				String linkName = this.item.getLinkName();
				return (linkName != null) ? linkName : super.getLinkName();
			}

			@Override
			public String getPrimaryKey() {
				String primaryKey = this.item.getPrimaryKey();
				return (primaryKey != null) ? primaryKey : super.getPrimaryKey();
			}

		}
		
	}

	private static class PrimaryKeysGroup extends AbstractGroup {

		private Set<String> primaryKeys;

		public PrimaryKeysGroup(AbstractItem parent, Collection<String> primaryKeys) {
			super(parent);
			this.setPrimaryKeys(primaryKeys);
		}

		public void setPrimaryKeys(Collection<String> primaryKeys) {
			if (primaryKeys == null) {
				throw new IllegalArgumentException("primaryKeys cannot be null.");
			}

			this.primaryKeys = new HashSet<String>(primaryKeys);
		}
		
		// MARK: Item
		@Override
		public Set<RequestConfigPart> getConfigParts() {
			Set<RequestConfigPart> parts = super.getConfigParts();
			
			parts.add(RequestConfigPart.PRIMARY_KEY);
			
			return parts;
		}

		// MARK: Group
		@Override
		protected int getGroupSize() {
			return this.primaryKeys.size();
		}
		
		@Override
		public List<? extends AbstractItem> createItemsForParent(AbstractItem parent) {
			List<ChildItem> items = new ArrayList<ChildItem>(); 
			
			for (String primaryKey : this.primaryKeys) {
				ChildItem item = new ChildItem(parent);
				item.setPrimaryKey(primaryKey);
				items.add(item);
			}
			
			return items;
		}
		
	}

	private static class LinkNamesGroup extends AbstractGroup {

		private Set<String> linkNames;

		public LinkNamesGroup(AbstractItem parent, Collection<String> linkNames) {
			super(parent);
			this.setLinkNames(linkNames);
		}

		public void setLinkNames(Collection<String> linkNames) {
			if (linkNames == null) {
				throw new IllegalArgumentException("linkNames cannot be null.");
			}

			this.linkNames = new HashSet<String>(linkNames);
		}
		
		// MARK: Item
		@Override
		public Set<RequestConfigPart> getConfigParts() {
			Set<RequestConfigPart> parts = super.getConfigParts();
			
			parts.add(RequestConfigPart.LINK_NAME);
			
			return parts;
		}

		// MARK: Group
		@Override
		protected int getGroupSize() {
			return this.linkNames.size();
		}
		
		@Override
		public List<? extends AbstractItem> createItemsForParent(AbstractItem parent) {
			List<ChildItem> items = new ArrayList<ChildItem>(); 
			
			for (String linkName : this.linkNames) {
				ChildItem item = new ChildItem(parent);
				item.setLinkName(linkName);
				items.add(item);
			}
			
			return items;
		}
		
	}
	
}
