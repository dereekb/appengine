package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.model.extension.links.system.components.LinkInfo;
import com.dereekb.gae.model.extension.links.system.components.LinkModelInfo;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystem;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemChangesResult;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemDelegate;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemDelegateInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequestValidator;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequestValidatorInstance;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;
import com.dereekb.gae.model.extension.links.system.modification.components.impl.LinkModificationImpl;
import com.dereekb.gae.model.extension.links.system.modification.exception.ChangesAlreadyComittedException;
import com.dereekb.gae.model.extension.links.system.modification.exception.ConflictingLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.exception.FailedLinkModificationSystemChangeException;
import com.dereekb.gae.model.extension.links.system.modification.exception.InvalidLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.exception.LinkModificationSystemRunnerAlreadyRunException;
import com.dereekb.gae.model.extension.links.system.modification.exception.TooManyChangeKeysException;
import com.dereekb.gae.model.extension.links.system.modification.exception.UndoChangesAlreadyExecutedException;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.exception.LinkChangeLinkSizeException;
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
        implements LinkModificationSystem, LinkModificationSystemRequestValidator {

	private static final Logger LOGGER = Logger.getLogger(LinkModificationSystemImpl.class.getName());

	private LinkSystem linkSystem;
	private LinkModificationSystemDelegate delegate;

	public LinkModificationSystemImpl(LinkSystem linkSystem, LinkModificationSystemDelegate delegate) {
		super();
		this.setLinkSystem(linkSystem);
		this.setDelegate(delegate);
	}

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
		private final LinkModificationSystemRequestValidatorInstance validator = LinkModificationSystemImpl.this.makeValidatorInstance();
		
		// MARK: LinkModificationSystemInstance
		@Override
		public void queueRequest(LinkModificationSystemRequest request)
		        throws UnavailableLinkException,
		            UnavailableLinkModelException,
		            InvalidLinkModificationSystemRequestException, 
		            ConflictingLinkModificationSystemRequestException {
			this.validateRequest(request);
			this.requests.add(request);
		}

		@Override
		public LinkModificationSystemChangesResult applyChangesAndCommit()
		        throws FailedLinkModificationSystemChangeException,
		            LinkModificationSystemRunnerAlreadyRunException {
			return this.applyChanges(true);
		}

		@Override
		public LinkModificationSystemChangesResult applyChanges()
		        throws FailedLinkModificationSystemChangeException,
		            LinkModificationSystemRunnerAlreadyRunException {
			return this.applyChanges(false);
		}

		public LinkModificationSystemChangesResult applyChanges(boolean autoCommit)
		        throws FailedLinkModificationSystemChangeException,
		            LinkModificationSystemRunnerAlreadyRunException {
			LinkModificationSystemChangesRunner runner = LinkModificationSystemImpl.this.makeRunner(this.requests, autoCommit);
			return runner.run();
		}

		// MARK: Internal
		private void validateRequest(LinkModificationSystemRequest request)
		        throws UnavailableLinkModelException,
		            UnavailableLinkException,
		            TooManyChangeKeysException, ConflictingLinkModificationSystemRequestException {
			this.validator.validateRequest(request);
		}

	}

	protected LinkModificationSystemChangesRunner makeRunner(List<LinkModificationSystemRequest> requests, boolean autoCommit) {
		List<RequestChanges> requestChanges = new ArrayList<RequestChanges>();

		for (LinkModificationSystemRequest request : requests) {
			RequestChanges requestChange = this.makeChangesForRequest(request);
			requestChanges.add(requestChange);
		}

		return new LinkModificationSystemChangesRunner(requestChanges, autoCommit);
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

		private final boolean autoCommitChanges;
		private List<RequestChanges> inputRequestChanges;
		private LinkModificationSystemDelegateInstance instance;

		public LinkModificationSystemChangesRunner(List<RequestChanges> inputRequestChanges, boolean autoCommit) {
			this.inputRequestChanges = inputRequestChanges;
			this.autoCommitChanges = autoCommit;
		}

		public LinkModificationSystemChangesResult run()
		        throws FailedLinkModificationSystemChangeException,
		            LinkModificationSystemRunnerAlreadyRunException {
			if (this.instance != null) {
				throw new LinkModificationSystemRunnerAlreadyRunException();
			} else {
				this.instance = LinkModificationSystemImpl.this.delegate.makeInstance();
			}

			List<LinkModification> modifications = RequestChanges
			        .getAllModificationsFromChanges(this.inputRequestChanges);

			try {
				this.runModifications(modifications);
				
				if (this.autoCommitChanges) {
					this.instance.commitChanges();
				}
			} catch (RuntimeException e) {
				try {
					// If this fails, log it.
					this.instance.undoChanges();
				} catch (Exception ee) {
					LOGGER.log(Level.SEVERE, "LinkModificationSystem Undo failed...", e);
					throw ee;
				}
				
				throw new FailedLinkModificationSystemChangeException(e);
			}
			
			return new LinkModificationSystemChangesResultImpl();
		}
		
		private class LinkModificationSystemChangesResultImpl implements LinkModificationSystemChangesResult {

			@Override
			public void commitChanges() throws ChangesAlreadyComittedException {
				LinkModificationSystemChangesRunner.this.instance.commitChanges();
			}
			
			@Override
			public void undoChanges() throws UndoChangesAlreadyExecutedException {
				LinkModificationSystemChangesRunner.this.instance.undoChanges();
			}

		}

		protected void runModifications(List<LinkModification> modifications) throws UnavailableModelException {
			this.testModifications(modifications);
			
			Map<String, HashMapWithList<ModelKey, LinkModification>> typeChangesMap = this
			        .buildTypeChangesMap(modifications);

			List<LinkModification> synchronizationChanges = new ArrayList<LinkModification>();

			for (Entry<String, HashMapWithList<ModelKey, LinkModification>> typeEntry : typeChangesMap.entrySet()) {
				LinkModificationResultSet resultSet = this.runModificationsForType(typeEntry.getKey(),
				        typeEntry.getValue());
				List<LinkModification> newSynchronizationChanges = this
				        .buildSynchronizationChangesFromResult(resultSet);
				synchronizationChanges.addAll(newSynchronizationChanges);
			}

			this.runSynchronizationChanges(synchronizationChanges);
		}

		private void runSynchronizationChanges(List<LinkModification> synchronizationChanges) throws UnavailableModelException {
			Map<String, HashMapWithList<ModelKey, LinkModification>> typeChangesMap = this
			        .buildTypeChangesMap(synchronizationChanges);

			for (Entry<String, HashMapWithList<ModelKey, LinkModification>> typeEntry : typeChangesMap.entrySet()) {
				this.runModificationsForType(typeEntry.getKey(), typeEntry.getValue());
			}
		}

		/**
		 * Tests that all input modifications are possible.
		 * <p>
		 * Mainly Set and Add are tested.
		 * 
		 * @param modifications {@link List}. Never {@code null}.
		 */
		private void testModifications(List<LinkModification> modifications) throws UnavailableModelException {
			LinkModificationSystemImpl.this.delegate.testModifications(modifications);
		}
		
		protected LinkModificationResultSet runModificationsForType(String type,
		                                                            HashMapWithList<ModelKey, LinkModification> keyedMap) throws UnavailableModelException {
			LinkModificationResultSet resultSet = this.instance.performModificationsForType(type, keyedMap);
			return resultSet;
		}

		protected List<LinkModification> buildSynchronizationChangesFromResult(LinkModificationResultSet resultSet) {
			Set<LinkModificationResult> results = resultSet.getResults();
			List<LinkModification> allModifications = new ArrayList<LinkModification>();

			for (LinkModificationResult result : results) {
				List<LinkModification> modifications = this.buildSynchronizationChangeFromResult(result);
				allModifications.addAll(modifications);
			}

			return allModifications;
		}

		protected List<LinkModification> buildSynchronizationChangeFromResult(LinkModificationResult result) {
			return LinkModificationSynchronizationBuilder.makeSynchronizationLinkModifications(result);
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

	// MARK: LinkModificationSystemRequestValidator
	@Override
	public LinkModificationSystemRequestValidatorInstance makeValidatorInstance() {
		return new LinkModificationSystemRequestValidatorInstanceImpl();
	}
	
	protected class LinkModificationSystemRequestValidatorInstanceImpl implements LinkModificationSystemRequestValidatorInstance {

		private Set<String> linkHashes = new HashSet<String>();
		
		@Override
		public void validateRequest(LinkModificationSystemRequest request)
		        throws UnavailableLinkModelException,
		            UnavailableLinkException,
		            TooManyChangeKeysException, ConflictingLinkModificationSystemRequestException {

			LinkInfo linkInfo = LinkModificationSystemImpl.this.getLinkInfoForRequest(request);

			switch (linkInfo.getLinkSize()) {
				case MANY:
					this.validateManyLink(linkInfo, request);
					break;
				case ONE:
					this.validateOneLink(linkInfo, request);
					break;
				default:
					throw new UnsupportedOperationException();
			}
			
			this.addAndAssertRequestUniqueLinkHash(request);
		}

		// MARK: One
		private void validateOneLink(LinkInfo linkInfo,
		                             LinkModificationSystemRequest request) throws TooManyChangeKeysException, LinkChangeLinkSizeException {
			this.assertOneLinkKeysCount(request);
			this.assertOneLinkChangeSize(request);
		}

		protected void assertOneLinkKeysCount(LinkModificationSystemRequest request) throws TooManyChangeKeysException {

			// Assert there is only one request being made per model's link.
			if (request.getKeys().size() > 1) {
				throw new TooManyChangeKeysException(request);
			}
			
		}
		
		protected void assertOneLinkChangeSize(LinkModificationSystemRequest request) throws LinkChangeLinkSizeException {
			switch (request.getLinkChangeType()) {
				case ADD:
				case REMOVE:
					throw new LinkChangeLinkSizeException(request);
				case SET:
				case CLEAR:
				default:
					break;
			}
		}

		// MARK: Many
		private void validateManyLink(LinkInfo linkInfo,
		                              LinkModificationSystemRequest request) {
			
			// Nothing special to validate...
			
		}
		
		// MARK: Shared
		protected void addAndAssertRequestUniqueLinkHash(LinkModificationSystemRequest request) throws ConflictingLinkModificationSystemRequestException {
			String uniqueLinkId = this.makeUniqueLinkId(request);
			
			if (this.linkHashes.contains(uniqueLinkId)) {
				throw new ConflictingLinkModificationSystemRequestException(request);
			} else {
				this.linkHashes.add(uniqueLinkId);
			}
		}

		private String makeUniqueLinkId(LinkModificationSystemRequest request) {
			ModelKey key = request.getPrimaryKey();
			String linkName = request.getLinkName();
			return key.getName() + "_" + linkName;
		}
		
	}

}
