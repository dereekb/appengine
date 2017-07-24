package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.links.system.components.LinkInfo;
import com.dereekb.gae.model.extension.links.system.components.LinkModelInfo;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationPair;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationPairState;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationPreTestPair;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationPreTestResult;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationPreTestResultInfo;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystem;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemChangesResult;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemDelegate;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemDelegateInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemInstanceOptions;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequestValidator;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequestValidatorInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemResult;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;
import com.dereekb.gae.model.extension.links.system.modification.components.impl.LinkModificationImpl;
import com.dereekb.gae.model.extension.links.system.modification.exception.ChangesAlreadyComittedException;
import com.dereekb.gae.model.extension.links.system.modification.exception.ConflictingLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.exception.FailedLinkModificationSystemChangeException;
import com.dereekb.gae.model.extension.links.system.modification.exception.InvalidLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.exception.InvalidLinkSizeLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.exception.LinkModificationSystemInstanceAlreadyRunException;
import com.dereekb.gae.model.extension.links.system.modification.exception.TooManyChangeKeysException;
import com.dereekb.gae.model.extension.links.system.modification.exception.UndoChangesAlreadyExecutedException;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeResult;
import com.dereekb.gae.model.extension.links.system.mutable.impl.MutableLinkChangeImpl;
import com.dereekb.gae.model.extension.links.system.readonly.LinkSystem;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMapWithList;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.collections.map.MapUtility;
import com.dereekb.gae.utilities.filters.FilterResults;

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

	private LinkModificationSystemInstanceOptions defaultOptions = new LinkModificationSystemInstanceOptionsImpl();
	
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
		return this.makeInstance(null);
	}

	@Override
	public LinkModificationSystemInstance makeInstance(LinkModificationSystemInstanceOptions options) {
		if (options == null) {
			options = this.defaultOptions;
		}
		
		return new LinkModificationSystemInstanceImpl(options);
	}

	// MARK: Instance
	protected class LinkModificationSystemInstanceImpl
	        implements LinkModificationSystemInstance {

		private final LinkModificationSystemInstanceOptions options;
		
		private final List<LinkModificationSystemRequest> requests = new ArrayList<LinkModificationSystemRequest>();
		private final LinkModificationSystemRequestValidatorInstance validator = LinkModificationSystemImpl.this.makeValidatorInstance();
		
		public LinkModificationSystemInstanceImpl(LinkModificationSystemInstanceOptions options) {
			this.options = options;
		}

		// MARK: LinkModificationSystemInstance
		@Override
		public LinkModificationSystemInstanceOptions getOptions() {
			return this.options;
		}
		
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
		public LinkModificationSystemChangesResult applyChanges()
		        throws FailedLinkModificationSystemChangeException,
		            LinkModificationSystemInstanceAlreadyRunException {
			LinkModificationSystemChangesRunner runner = LinkModificationSystemImpl.this.makeRunner(this.requests, this.options);
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

	protected LinkModificationSystemChangesRunner makeRunner(List<LinkModificationSystemRequest> requests, LinkModificationSystemInstanceOptions options) {
		boolean atomic = options.isAtomic();
		boolean autoCommit = options.isAutoCommit();
		
		List<RequestInstance> requestChanges = new ArrayList<RequestInstance>();

		for (LinkModificationSystemRequest request : requests) {
			RequestInstance requestChange = this.makeChangesForRequest(request);
			requestChanges.add(requestChange);
		}

		return new LinkModificationSystemChangesRunner(requestChanges, atomic, autoCommit);
	}

	/**
	 * Makes all initial changes for the input request.
	 * <p>
	 * These changes do not
	 * 
	 * @param request
	 * @return
	 */
	protected RequestInstance makeChangesForRequest(LinkModificationSystemRequest request) throws InvalidLinkModificationSystemRequestException {
		LinkInfo info = this.getLinkInfoForRequest(request);
		
		ModelKey primaryKey = this.convertPrimaryKeyForRequest(info, request);
		Collection<ModelKey> keys = this.convertKeysForRequest(info, request);
		MutableLinkChange change = MutableLinkChangeImpl.make(request.getLinkChangeType(), keys);

		LinkModification modification = new LinkModificationImpl(primaryKey, info, change);
		return new RequestInstance(modification, request);
	}
	
	private ModelKey convertPrimaryKeyForRequest(LinkInfo info,
	                                             LinkModificationSystemRequest request) throws InvalidLinkModificationSystemRequestException {
		
		LinkModelInfo linkModelInfo = info.getLinkModelInfo();
		ModelKeyType keyType = linkModelInfo.getModelKeyType();
		String primaryKeyString = request.getPrimaryKey();
		
		return ModelKey.convert(keyType, primaryKeyString);
	}

	private Collection<ModelKey> convertKeysForRequest(LinkInfo info, LinkModificationSystemRequest request) throws InvalidLinkModificationSystemRequestException {
		ModelKeyType keyType = info.getModelKeyType();
		Set<String> keyStrings = request.getKeys();

		Collection<ModelKey> convertedKeys = null;
		
		try {
			convertedKeys = ModelKey.convert(keyType, keyStrings);
		} catch (ConversionFailureException e) {
			String message = "Invalid Model Key: " + e.getMessage();
			throw new InvalidLinkModificationSystemRequestException(request, message);
		}
		
		return convertedKeys;
	}

	/**
	 * Internal instance object that wraps changes initiated by a request.
	 * 
	 * @author dereekb
	 *
	 */
	protected static class RequestInstance implements LinkModificationSystemResult {

		private final LinkModification modification;
		private final LinkModificationSystemRequest request;
		
		private LinkModificationPreTestResultImpl testResult = new LinkModificationPreTestResultImpl();
		
		private LinkModificationPairImpl primaryPair;
		private List<LinkModificationPairImpl> secondaryPairs = new ArrayList<LinkModificationPairImpl>();

		public RequestInstance(LinkModification modification, LinkModificationSystemRequest request) {
			this.modification = modification;
			this.request = request;
			
			this.primaryPair = new LinkModificationPairImpl(modification);
		}

		// MARK: LinkModificationSystemResult
		@Override
		public LinkModificationSystemRequest getRequest() {
			return this.request;
		}

		@Override
		public LinkModificationPreTestResultImpl getPreTestResults() {
			return this.testResult;
		}

		@Override
		public LinkModificationResult getPrimaryResult() {
			return this.primaryPair;
		}

		@Override
		public List<LinkModificationResult> getSynchronizationResults() {
			return new ArrayList<LinkModificationResult>(this.secondaryPairs);
		}
		
		// MARK: Pairs
		public LinkModificationPairImpl getPrimaryPair() {
			return this.primaryPair;
		}
		
		public List<LinkModificationPairImpl> getSecondaryPairs() {
			return this.secondaryPairs;
		}

		public List<LinkModificationPair> addSecondaryPairs(List<LinkModification> syncModifications) {
			List<LinkModificationPair> pairs = new ArrayList<LinkModificationPair>();
			
			for (LinkModification modification : syncModifications) {
				LinkModificationPair pair =this.addSecondaryPair(modification);
				pairs.add(pair);
			}
			
			return pairs;
		}

		public LinkModificationPair addSecondaryPair(LinkModification modification) {
			LinkModificationPairImpl secondary = new LinkModificationPairImpl(modification);
			
			this.secondaryPairs.add(secondary);
			
			return secondary;
		}		

		public boolean isSecondaryResultsSuccessful() {
			boolean successful = true;
			
			for (LinkModificationPairImpl pair : this.secondaryPairs) {
				if (pair.isSuccessful() == false) {
					successful = false;
					break;
				}
			}
			
			return successful;
		}
		
		private class LinkModificationPairImpl implements LinkModificationPair, LinkModificationResult {
			
			private final LinkModification modification;

			private LinkModificationPairState state = LinkModificationPairState.INIT;
			
			private LinkModificationResult result;
			private LinkModificationResult undoResult;

			public LinkModificationPairImpl(LinkModification modification) {
				this.modification = modification;
			}

			// MARK: LinkModificationPair
			@Override
			public LinkModification getLinkModification() {
				return this.modification;
			}
			
			@Override
			public void setLinkModificationResult(LinkModificationResult result) {
				if (result != null) {
					if (result.isSuccessful()) {
						this.state = LinkModificationPairState.SUCCESS;
					} else {
						this.state = LinkModificationPairState.FAILED;
					}
				} else {
					this.state = LinkModificationPairState.INIT;
				}
				
				this.result = result;
			}

			@Override
			public LinkModificationPairState getState() {
				return this.state;
			}

			@Override
			public LinkModificationResult getLinkModificationResult() {
				return this.result;
			}

			@Override
			public LinkModificationResult getUndoResult() {
				return this.undoResult;
			}

			@Override
			public void setUndoResult(LinkModificationResult result) {
				this.state = LinkModificationPairState.UNDONE;
				this.undoResult = result;
			}

			@Override
			public ModelKey keyValue() {
				return this.modification.keyValue();
			}
			
			// MARK: LinkModificationResult
			@Override
			public boolean isModelModified() {
				return this.result.isModelModified();
			}

			@Override
			public boolean isSuccessful() {
				return this.result.isSuccessful();
			}

			@Override
			public MutableLinkChangeResult getLinkChangeResult() {
				return this.result.getLinkChangeResult();
			}

		}
		
		private class LinkModificationPreTestResultImpl implements LinkModificationPreTestResult, LinkModificationPreTestPair {

			private LinkModificationPreTestResultInfo resultInfo;
			
			@Override
			public LinkModification getLinkModification() {
				return RequestInstance.this.modification;
			}

			@Override
			public void setResultInfo(LinkModificationPreTestResultInfo resultInfo) {
				this.resultInfo = resultInfo;
			}

			@Override
			public boolean isPassed() {
				return this.resultInfo.isPassed();
			}

			@Override
			public boolean isMissingPrimaryKey() {
				return this.resultInfo.isMissingPrimaryKey();
			}

			@Override
			public Set<ModelKey> getMissingTargetKeys() {
				return this.resultInfo.getMissingTargetKeys();
			}
			
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

		private final boolean atomic;
		private final boolean autoCommitChanges;
		private List<RequestInstance> inputRequestChanges;
		private LinkModificationSystemDelegateInstance instance;

		public LinkModificationSystemChangesRunner(List<RequestInstance> inputRequestChanges, boolean atomic, boolean autoCommit) {
			this.inputRequestChanges = inputRequestChanges;
			this.atomic = atomic;
			this.autoCommitChanges = autoCommit;
		}

		public LinkModificationSystemChangesResult run()
		        throws FailedLinkModificationSystemChangeException,
		            LinkModificationSystemInstanceAlreadyRunException {
			if (this.instance != null) {
				throw new LinkModificationSystemInstanceAlreadyRunException();
			} else {
				this.instance = LinkModificationSystemImpl.this.delegate.makeInstance();
			}

			List<LinkModificationSystemResult> results = null;
			
			try {
				results = this.runModifications();
				
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
			
			return new LinkModificationSystemChangesResultImpl(results);
		}
		
		private class LinkModificationSystemChangesResultImpl implements LinkModificationSystemChangesResult {

			private List<LinkModificationSystemResult> results;
			
			public LinkModificationSystemChangesResultImpl(List<LinkModificationSystemResult> results) {
				super();
				this.results = results;
			}

			@Override
			public List<LinkModificationSystemResult> getResults() {
				return this.results;
			}

			@Override
			public void commitChanges() throws ChangesAlreadyComittedException {
				LinkModificationSystemChangesRunner.this.instance.commitChanges();
			}
			
			@Override
			public void undoChanges() throws UndoChangesAlreadyExecutedException {
				LinkModificationSystemChangesRunner.this.instance.undoChanges();
			}

		}

		protected List<LinkModificationSystemResult> runModifications() throws UnavailableModelException {
			
			this.preTestModifications(this.inputRequestChanges);
			
			FilterResults<RequestInstance> filterResults = this.filterPassingPreTestResults(this.inputRequestChanges);
			
			// Assert all tests passed if atomic.
			if (this.atomic) {
				this.assertAllPassedPreTestResults(filterResults);
			}
			
			List<RequestInstance> passingRequests = filterResults.getPassingObjects();

			// Do Primary Changes
			this.runPrimaryModificationsForRequestInstances(passingRequests);

			FilterResults<RequestInstance> primaryFilterResults = this.filterSuccessfulPrimaryModifications(this.inputRequestChanges);
			
			// Assert all primary changes passed if atomic.
			if (this.atomic) {
				this.assertAllPrimaryModificationsPassed(primaryFilterResults);
			}
			
			passingRequests = primaryFilterResults.getPassingObjects();
			
			// Do Synchronization Changes
			this.runSynchronizationChangesForRequestInstances(passingRequests);
			
			FilterResults<RequestInstance> secondaryFilterResults = this.filterSuccessfulSecondaryModifications(this.inputRequestChanges);
			
			// Assert all sync changes passed if atomic.
			if (this.atomic) {
				this.assertAllSecondaryModificationsPassed(secondaryFilterResults);
			}

			passingRequests = secondaryFilterResults.getPassingObjects();
			
			return new ArrayList<LinkModificationSystemResult>(this.inputRequestChanges);
		}

		// MARK: Pre Tests
		/**
		 * Tests that all input modifications are possible.
		 * <p>
		 * Mainly Set and Add are tested.
		 * 
		 * @param inputRequestChanges {@link List}. Never {@code null}.
		 */
		protected void preTestModifications(List<RequestInstance> inputRequestChanges) throws UnavailableModelException {
			List<LinkModificationPreTestPair> pairs = new ArrayList<LinkModificationPreTestPair>();
			
			for (RequestInstance request : inputRequestChanges) {
				pairs.add(request.getPreTestResults());
			}
			
			LinkModificationSystemImpl.this.delegate.preTestModifications(pairs);
		}
		
		protected FilterResults<RequestInstance> filterPassingPreTestResults(List<RequestInstance> inputRequestChanges) {
			FilterResults<RequestInstance> results = new FilterResults<RequestInstance>();
			
			for (RequestInstance request : inputRequestChanges) {
				LinkModificationPreTestResult result = request.getPreTestResults();
				
				results.addWithBoolean(result.isPassed(), request);
			}
			
			return results;
		}
		
		protected void assertAllPassedPreTestResults(FilterResults<RequestInstance> filterResults) throws AtomicOperationException {
			
			// TODO: Assert and throw atomic operation exception if fails.
			
		}
		
		// MARK: Primary Modifications
		private void runPrimaryModificationsForRequestInstances(List<RequestInstance> passingRequests) {
			List<LinkModificationPair> primaryPairs = new ArrayList<LinkModificationPair>();

			for (RequestInstance request : this.inputRequestChanges) {
				LinkModificationPair primaryPair = request.getPrimaryPair();
				primaryPairs.add(primaryPair);
			}

			this.runModifications(primaryPairs);
		}

		private FilterResults<RequestInstance> filterSuccessfulPrimaryModifications(List<RequestInstance> inputRequestChanges2) {
			FilterResults<RequestInstance> results = new FilterResults<RequestInstance>();
			
			for (RequestInstance request : this.inputRequestChanges) {
				LinkModificationResult result = request.getPrimaryResult();
				results.addWithBoolean(result.isSuccessful(), request);
			}
			
			return results;
		}

		private void assertAllPrimaryModificationsPassed(FilterResults<RequestInstance> filterResults) {
			// TODO Auto-generated method stub
			
		}
		
		// MARK: Synchronization Modifications
		private void runSynchronizationChangesForRequestInstances(List<RequestInstance> passingRequests) {
			List<LinkModificationPair> secondaryPairs = new ArrayList<LinkModificationPair>();

			for (RequestInstance request : this.inputRequestChanges) {
				LinkModificationResult result = request.getPrimaryResult();
				
				List<LinkModification> syncModifications = this.buildSynchronizationChangeFromResult(result);
				List<LinkModificationPair> syncPairs = request.addSecondaryPairs(syncModifications);
				
				secondaryPairs.addAll(syncPairs);
			}

			this.runModifications(secondaryPairs);
		}

		private FilterResults<RequestInstance> filterSuccessfulSecondaryModifications(List<RequestInstance> inputRequestChanges) {
			FilterResults<RequestInstance> results = new FilterResults<RequestInstance>();
			
			for (RequestInstance request : this.inputRequestChanges) {
				boolean secondaryResultsAllSuccessful = request.isSecondaryResultsSuccessful();
				results.addWithBoolean(secondaryResultsAllSuccessful, request);
			}
			
			return results;
		}

		private void assertAllSecondaryModificationsPassed(FilterResults<RequestInstance> secondaryFilterResults) {

			// TODO: Assert and throw atomic operation exception if fails.
			
		}
		
		protected void runModifications(List<LinkModificationPair> modifications) {
			Map<String, HashMapWithList<ModelKey, LinkModificationPair>> typeChangesMap = this
			        .buildTypeChangesMap(modifications);

			for (Entry<String, HashMapWithList<ModelKey, LinkModificationPair>> typeEntry : typeChangesMap.entrySet()) {
				this.runModificationsForType(typeEntry.getKey(), typeEntry.getValue());
			}
		}

		protected LinkModificationResultSet runModificationsForType(String type,
		                                                            HashMapWithList<ModelKey, LinkModificationPair> hashMapWithList) throws UnavailableModelException {
			return this.instance.performModificationsForType(type, hashMapWithList, this.atomic);
		}

		// MARK: Internal
		private Map<String, HashMapWithList<ModelKey, LinkModificationPair>> buildTypeChangesMap(List<LinkModificationPair> linkModificationPairs) {
			CaseInsensitiveMapWithList<LinkModificationPair> typesMap = new CaseInsensitiveMapWithList<LinkModificationPair>();

			for (LinkModificationPair modificationPair : linkModificationPairs) {
				LinkModification modification = modificationPair.getLinkModification();
				String type = modification.getLinkModelType();
				typesMap.add(type, modificationPair);
			}

			Map<String, HashMapWithList<ModelKey, LinkModificationPair>> typeKeyedMap = new HashMap<String, HashMapWithList<ModelKey, LinkModificationPair>>();

			for (Entry<String, List<LinkModificationPair>> typesEntry : typesMap.entrySet()) {
				HashMapWithList<ModelKey, LinkModificationPair> keyedMap = MapUtility
				        .makeHashMapWithList(typesEntry.getValue());
				typeKeyedMap.put(typesEntry.getKey(), keyedMap);
			}

			return typeKeyedMap;
		}
		
		protected List<LinkModification> buildSynchronizationChangeFromResult(LinkModificationResult result) {
			return LinkModificationSynchronizationBuilder.makeSynchronizationLinkModifications(result);
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
		                             LinkModificationSystemRequest request) throws TooManyChangeKeysException, InvalidLinkSizeLinkModificationSystemRequestException {
			this.assertOneLinkKeysCount(request);
			this.assertOneLinkChangeSize(request);
		}

		protected void assertOneLinkKeysCount(LinkModificationSystemRequest request) throws TooManyChangeKeysException {

			// Assert there is only one request being made per model's link.
			if (request.getKeys().size() > 1) {
				throw new TooManyChangeKeysException(request);
			}
			
		}
		
		protected void assertOneLinkChangeSize(LinkModificationSystemRequest request) throws InvalidLinkSizeLinkModificationSystemRequestException {
			switch (request.getLinkChangeType()) {
				case ADD:
				case REMOVE:
					throw new InvalidLinkSizeLinkModificationSystemRequestException(request);
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
			String key = request.getPrimaryKey();
			String linkName = request.getLinkName();
			return key + "_" + linkName;
		}
		
	}

}
