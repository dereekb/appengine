package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dereekb.gae.model.extension.links.system.components.LinkInfo;
import com.dereekb.gae.model.extension.links.system.components.LinkModelInfo;
import com.dereekb.gae.model.extension.links.system.components.LinkSize;
import com.dereekb.gae.model.extension.links.system.components.Relation;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationChangesMap;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystem;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemDelegate;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemDelegateInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationImpl;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;
import com.dereekb.gae.model.extension.links.system.modification.exception.InvalidLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.exception.TooManyChangeKeysException;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.model.extension.links.system.mutable.impl.MutableLinkChangeImpl;
import com.dereekb.gae.model.extension.links.system.readonly.LinkSystem;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMapWithList;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.collections.map.MapUtility;

/**
 * {@link LinkModificationSystem} implementation.
 * 
 * @author dereekb
 *
 */
public class LinkModificationSystemImpl
        implements LinkModificationSystem {

	private LinkSystem linkSystem;
	private LinkModificationSystemDelegate delegate;

	public LinkSystem getLinkSystem() {
		return this.linkSystem;
	}

	public void setLinkSystem(LinkSystem linkSystem) {
		if (linkSystem == null) {
			throw new IllegalArgumentException("linkSystem cannot be null.");
		}

		this.linkSystem = linkSystem;
	}

	public LinkModificationSystemDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(LinkModificationSystemDelegate delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	// MARK: LinkModificationSystem
	@Override
	public LinkModificationSystemInstance makeInstance() {
		return new LinkModificationSystemInstanceImpl();
	}

	// MARK: Instance
	protected class LinkModificationSystemInstanceImpl
	        implements LinkModificationSystemInstance {

		private final List<LinkModificationSystemRequest> requests = new ArrayList<LinkModificationSystemRequest>();

		// MARK: LinkModificationSystemInstance
		@Override
		public void queueRequest(LinkModificationSystemRequest request)
		        throws UnavailableLinkException,
		            UnavailableLinkModelException,
		            InvalidLinkModificationSystemRequestException {
			this.validateRequest(request);
			this.requests.add(request);
		}

		@Override
		public void applyChanges() {
			LinkModificationSystemChangesRunner runner = LinkModificationSystemImpl.this.makeRunner(this.requests);
			runner.run();
		}

		// MARK: Internal
		private void validateRequest(LinkModificationSystemRequest request)
		        throws UnavailableLinkModelException,
		            UnavailableLinkException,
		            TooManyChangeKeysException {

			LinkInfo linkInfo = LinkModificationSystemImpl.this.getLinkInfoForRequest(request);

			// Throw if multiple items are passed to a single item link.
			if (linkInfo.getLinkSize() == LinkSize.ONE && request.getKeys().size() > 1) {
				throw new TooManyChangeKeysException(request);
			}

			// TODO: Assert that only 1 change is occurring per model's link.
			// I.E. do not allow multiple changes to the same link on the same model, as it
			// is unsafe.

			// TODO: Validate further for the request.
		}

	}

	protected LinkModificationSystemChangesRunner makeRunner(List<LinkModificationSystemRequest> requests) {
		List<RequestChanges> requestChanges = new ArrayList<RequestChanges>();

		for (LinkModificationSystemRequest request : requests) {
			RequestChanges requestChange = this.makeChangesForRequest(request);
			requestChanges.add(requestChange);
		}

		return new LinkModificationSystemChangesRunner(requestChanges);
	}

	/**
	 * Makes all initial changes for the input request.
	 * <p>
	 * These changes do not
	 * 
	 * @param request
	 * @return
	 */
	protected RequestChanges makeChangesForRequest(LinkModificationSystemRequest request) {
		LinkInfo info = this.getLinkInfoForRequest(request);
		MutableLinkChange change = MutableLinkChangeImpl.make(request.getLinkChangeType(), request.getKeys());

		LinkModification primaryModification = new LinkModificationImpl(request.getPrimaryKey(), info, change);
		return new RequestChanges(primaryModification);
	}

	/**
	 * @deprecated Attempting to predict "required future changes" is dangerous.
	 *             Just let them be built naturally from the results.
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private List<LinkModification> buildSecondaryModifications(Relation relation,
	                                                           LinkModificationSystemRequest request) {
		boolean isOptional = false;

		/*
		 * Set isOptional based on changes being made.
		 * 
		 * Removal requests that cannot load the target model are ignored will
		 * not caused the transaction changes to fail.
		 */
		MutableLinkChangeType primaryLinkChangeType = request.getLinkChangeType();
		MutableLinkChangeType linkChangeType = null;

		switch (primaryLinkChangeType) {
			case ADD:
				isOptional = false;
				break;
			case SET:
				isOptional = false;
				break;
			case CLEAR:
			case REMOVE:
				isOptional = true;
				break;
			default:
				break;
		}

		List<LinkModification> modifications = null;

		switch (relation.getRelationSize()) {
			// Bi-directional
			case MANY_TO_MANY:
			case MANY_TO_ONE:
			case ONE_TO_MANY:
			case ONE_TO_ONE:
				// Perform "mirrored" changes on all children.
				modifications = new ArrayList<LinkModification>();

				LinkInfo relationLink = relation.getRelationLink();
				MutableLinkChange relationChange = MutableLinkChangeImpl.make(linkChangeType, request.getPrimaryKey());

				for (ModelKey key : request.getKeys()) {
					LinkModification modification = new LinkModificationImpl(key, relationLink, relationChange,
					        isOptional);
					modifications.add(modification);
				}

				break;
			// None
			case ONE_TO_NONE:
				// Do nothing.
				modifications = Collections.emptyList();
				break;
			default:
				throw new UnsupportedOperationException("Unknown relation change type.");
		}

		return null;
	}

	protected static class RequestChanges {

		private LinkModification primaryModification;

		public RequestChanges(LinkModification primaryModification) {
			this.setPrimaryModification(primaryModification);
		}

		public LinkModification getPrimaryModification() {
			return this.primaryModification;
		}

		public void setPrimaryModification(LinkModification primaryModification) {
			if (primaryModification == null) {
				throw new IllegalArgumentException("primaryModification cannot be null.");
			}

			this.primaryModification = primaryModification;
		}

		public static List<LinkModification> getAllModificationsFromChanges(Iterable<RequestChanges> changes) {
			List<LinkModification> modifications = new ArrayList<LinkModification>();

			for (RequestChanges change : changes) {
				modifications.add(change.getPrimaryModification());
			}

			return modifications;
		}

	}

	protected LinkInfo getLinkInfoForRequest(LinkModificationSystemRequest request)
	        throws UnavailableLinkModelException,
	            UnavailableLinkException {

		// Assert Link Type Exists
		String modelType = request.getLinkModelType();
		LinkModelInfo linkModelInfo = LinkModificationSystemImpl.this.linkSystem.loadLinkModelInfo(modelType);

		// Assert Link Exists
		String linkName = request.getLinkName();
		LinkInfo linkInfo = linkModelInfo.getLinkInfo(linkName);

		return linkInfo;
	}

	// MARK: Runner
	protected class LinkModificationSystemChangesRunner {

		private List<RequestChanges> inputRequestChanges;
		private LinkModificationSystemDelegateInstance instance;

		private LinkModificationChangesMap changesMap = new LinkModificationChangesMapImpl();

		public LinkModificationSystemChangesRunner(List<RequestChanges> inputRequestChanges) {
			this.inputRequestChanges = inputRequestChanges;
		}

		public void run() {
			if (this.instance != null) {
				// TODO: Throw specific exception.
				throw new RuntimeException();
			} else {
				this.instance = LinkModificationSystemImpl.this.delegate.makeInstance();
			}

			List<LinkModification> modifications = RequestChanges
			        .getAllModificationsFromChanges(this.inputRequestChanges);

			try {
				this.run(modifications);
				this.instance.commitChanges();
			} catch (Exception e) {
				this.instance.revertChanges();
				// TODO: Throw failed.exception.
			}
		}

		protected void run(List<LinkModification> modifications) {
			modifications = this.changesMap.filterRedundantChanges(modifications);

			List<LinkModification> synchronizationChanges = new ArrayList<LinkModification>();

			Map<String, HashMapWithList<ModelKey, LinkModification>> typeChangesMap = this
			        .buildTypeChangesMap(modifications);

			for (Entry<String, HashMapWithList<ModelKey, LinkModification>> typeEntry : typeChangesMap.entrySet()) {
				List<LinkModification> changes = this.runModificationsForType(typeEntry.getKey(), typeEntry.getValue());
				synchronizationChanges.addAll(changes);
			}

			/*
			 * TODO: Going to have to figure out the best way to prevent
			 * redundant changes from happening and taking up CPU time.
			 * 
			 * For instance, if A has already been updated to reference A, then
			 * B is updated and returns that A needs to be updated, this needs
			 * to be marked as redundant and ignored.
			 * 
			 * Realistically though, this might not be too complex to be worth
			 * it. Would have to build a map of changes made then use
			 * it to filter out redundant requests, which isn't that
			 * difficult I suppose.
			 */

			// Run Synchronization Changes
			this.run(synchronizationChanges);
		}

		protected List<LinkModification> runModificationsForType(String type,
		                                                         HashMapWithList<ModelKey, LinkModification> keyedMap) {
			LinkModificationResultSet resultSet = this.instance.performModificationsForType(type, keyedMap);
			this.changesMap.addResultSet(resultSet);
			return this.buildSynchronizationChangesFromResult(resultSet);
		}

		protected List<LinkModification> buildSynchronizationChangesFromResult(LinkModificationResultSet resultSet) {

			return null;	// TODO: get synchronization changes.
		}

		// MARK: Internal
		private Map<String, HashMapWithList<ModelKey, LinkModification>> buildTypeChangesMap(List<LinkModification> modifications) {
			CaseInsensitiveMapWithList<LinkModification> typesMap = new CaseInsensitiveMapWithList<LinkModification>();

			for (LinkModification modification : modifications) {
				String type = modification.getLinkModelType();
				typesMap.add(type, modification);
			}

			Map<String, HashMapWithList<ModelKey, LinkModification>> typeKeyedMap = new HashMap<String, HashMapWithList<ModelKey, LinkModification>>();

			for (Entry<String, List<LinkModification>> typesEntry : typesMap.entrySet()) {
				HashMapWithList<ModelKey, LinkModification> keyedMap = MapUtility
				        .makeHashMapWithList(typesEntry.getValue());
				typeKeyedMap.put(typesEntry.getKey(), keyedMap);
			}

			return typeKeyedMap;
		}

	}

}
