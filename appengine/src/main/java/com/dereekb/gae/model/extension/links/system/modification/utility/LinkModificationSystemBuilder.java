package com.dereekb.gae.model.extension.links.system.modification.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystem;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemDelegate;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemEntry;
import com.dereekb.gae.model.extension.links.system.modification.impl.LinkModificationSystemDelegateImpl;
import com.dereekb.gae.model.extension.links.system.modification.impl.LinkModificationSystemEntryImpl;
import com.dereekb.gae.model.extension.links.system.modification.impl.LinkModificationSystemImpl;
import com.dereekb.gae.model.extension.links.system.mutable.FullLinkModelAccessor;
import com.dereekb.gae.model.extension.links.system.mutable.impl.MutableLinkSystemBuilderImpl;
import com.dereekb.gae.model.extension.links.system.readonly.LinkModelAccessor;
import com.dereekb.gae.model.extension.links.system.readonly.LinkSystem;
import com.dereekb.gae.model.extension.read.exception.UnavailableTypesException;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;

/**
 * Used for building a {@link LinkSystem} and {@link LinkModificationSystem},
 * 
 * @author dereekb
 *
 */
@SuppressWarnings("rawtypes")
public class LinkModificationSystemBuilder {

	private List<LinkModificationSystemBuilderEntry> entries;

	private transient CaseInsensitiveMap<? extends FullLinkModelAccessor> fullAccessors;
	private transient MutableLinkSystemBuilderImpl mutableLinkSystemBuilder;

	private transient LinkSystem linkSystem;
	private transient LinkModificationSystem linkModificationSystem;

	public LinkModificationSystemBuilder(List<LinkModificationSystemBuilderEntry> entries) {
		this.setEntries(entries);
	}

	public List<LinkModificationSystemBuilderEntry> getEntries() {
		return this.entries;
	}

	public void setEntries(List<LinkModificationSystemBuilderEntry> entries) {
		if (entries == null) {
			throw new IllegalArgumentException("entries cannot be null.");
		}

		this.entries = entries;
	}

	// MARK: Builder
	public LinkSystem makeLinkSystem() {
		if (this.linkSystem == null) {
			MutableLinkSystemBuilderImpl builder = this.getMutableLinkSystemBuilder();
			Map<String, ? extends LinkModelAccessor> accessors = this.getFullLinkModelAccessors();
			this.linkSystem = builder.makeLinkSystem(accessors);
		}

		return this.linkSystem;
	}

	@SuppressWarnings("unchecked")
	public LinkModificationSystem makeLinkModifictionSystem() {
		if (this.linkModificationSystem == null) {
			LinkSystem linkSystem = this.makeLinkSystem();

			Map<String, ? extends FullLinkModelAccessor> accessors = this.getFullLinkModelAccessors();
			Map<String, LinkModificationSystemEntry> entriesMap = new HashMap<String, LinkModificationSystemEntry>();

			for (LinkModificationSystemBuilderEntry entry : this.entries) {
				String entryType = entry.getLinkModelType();
				FullLinkModelAccessor accessor = accessors.get(entryType);
				Updater updater = entry.getUpdater();
				TaskRequestSender reviewTaskSender = entry.getReviewTaskSender();

				LinkModificationSystemEntryImpl systemEntry = new LinkModificationSystemEntryImpl(accessor, updater,
				        reviewTaskSender);
				entriesMap.put(entryType, systemEntry);
			}

			LinkModificationSystemDelegate delegate = new LinkModificationSystemDelegateImpl(entriesMap);
			this.linkModificationSystem = new LinkModificationSystemImpl(linkSystem, delegate);
		}

		return this.linkModificationSystem;
	}
	
	public FullLinkModelAccessor getAccessorForType(String type) throws UnavailableTypesException {
		 FullLinkModelAccessor accessor = this.getFullLinkModelAccessors().get(type);
		 
		 if (accessor == null) {
			 throw new UnavailableTypesException(type);
		 }
		 
		 return accessor;
	}

	// MARK: Internal
	private CaseInsensitiveMap<? extends FullLinkModelAccessor> getFullLinkModelAccessors() {
		if (this.fullAccessors == null) {
			this.fullAccessors = this.buildFullLinkModelAccessors();
		}

		return this.fullAccessors;
	}

	@SuppressWarnings("unchecked")
	private CaseInsensitiveMap<? extends FullLinkModelAccessor> buildFullLinkModelAccessors() {
		CaseInsensitiveMap<FullLinkModelAccessor> map = new CaseInsensitiveMap<FullLinkModelAccessor>();
		MutableLinkSystemBuilderImpl builder = this.getMutableLinkSystemBuilder();

		for (LinkModificationSystemBuilderEntry entry : this.entries) {
			String type = entry.getLinkModelType();
			FullLinkModelAccessor accessor = builder.makeAccessor(entry);
			map.put(type, accessor);
		}

		return map;
	}

	private MutableLinkSystemBuilderImpl getMutableLinkSystemBuilder() {
		if (this.mutableLinkSystemBuilder == null) {
			this.mutableLinkSystemBuilder = this.buildMutableLinkSystemBuilder();
		}

		return this.mutableLinkSystemBuilder;
	}

	private MutableLinkSystemBuilderImpl buildMutableLinkSystemBuilder() {
		return new MutableLinkSystemBuilderImpl(this.entries);
	}

}
