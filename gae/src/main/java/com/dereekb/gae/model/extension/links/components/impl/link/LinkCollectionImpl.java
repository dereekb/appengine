package com.dereekb.gae.model.extension.links.components.impl.link;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkData;
import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.Relation;
import com.dereekb.gae.model.extension.links.components.RelationResult;
import com.dereekb.gae.model.extension.links.components.exception.RelationChangeException;
import com.dereekb.gae.model.extension.links.components.exception.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.components.impl.LinkDataImpl;
import com.dereekb.gae.model.extension.links.components.impl.RelationResultImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.utilities.collections.list.SetUtility.SetInfo;

/**
 * {@link Link} implementation that wraps a {@link Collection} that is retained
 * by the model being edited.
 *
 * Usage of this implementation comes with the danger that any external changes
 * made to the model that cause it to no longer reference the collection wrapped
 * will result in an inconsistency.
 *
 * @author dereekb
 *
 * @param <T>
 *            reference type
 */
@Deprecated
public class LinkCollectionImpl<T>
        implements Link {

	private final LinkInfo linkInfo;

	/**
	 * The collection reference to update.
	 * 
	 * Cannot be changed once set.
	 */
	private Collection<T> collection;

	/**
	 * Used for converting the {@link ModelKey} into the collection's element.
	 */
	private BidirectionalConverter<T, ModelKey> converter;

	public LinkCollectionImpl(LinkInfo linkInfo,
	        Collection<T> collection,
	        BidirectionalConverter<T, ModelKey> converter) throws IllegalArgumentException {
		this.linkInfo = linkInfo;
		this.setCollection(collection);
		this.setConverter(converter);
	}

	public Collection<T> getCollection() {
		return this.collection;
	}

	private void setCollection(Collection<T> collection) {
		if (collection == null) {
			throw new IllegalArgumentException("Collection cannot be null.");
		}

		this.collection = collection;
	}

	public BidirectionalConverter<T, ModelKey> getConverter() {
		return this.converter;
	}

	private void setConverter(BidirectionalConverter<T, ModelKey> converter) {
		if (converter == null) {
			throw new IllegalArgumentException("Converter cannot be null.");
		}

		this.converter = converter;
	}

	// Link
	@Override
	public String getLinkName() {
		return this.linkInfo.getLinkName();
	}

	@Override
	public ModelKey getLinkModelKey() {
		return this.linkInfo.getLinkModelKey();
	}

	@Override
	public LinkTarget getLinkTarget() {
		return this.linkInfo.getLinkTarget();
	}

	// MARK: Link
	@Override
	public LinkData getLinkData() {
		List<ModelKey> keys = this.converter.convertTo(this.collection);
		LinkData linkData = new LinkDataImpl(this.linkInfo, keys);
		return linkData;
	}

	@Override
	public RelationResult addRelation(Relation change) throws RelationChangeException, UnavailableLinkException {
		List<ModelKey> keys = change.getRelationKeys();
		RelationResultImpl result = this.buildRelationResult(true, keys);

		List<T> items = this.converter.convertFrom(keys);
		this.collection.addAll(items);

		return result;
	}

	@Override
	public RelationResult removeRelation(Relation change) throws RelationChangeException {
		List<ModelKey> keys = change.getRelationKeys();
		RelationResultImpl result = this.buildRelationResult(false, keys);

		List<T> items = this.converter.convertFrom(keys);
		this.collection.removeAll(items);

		return result;
	}

	private RelationResultImpl buildRelationResult(boolean adding,
	                                               List<ModelKey> keys) {
		List<ModelKey> currentKeys = this.converter.convertTo(this.collection);
		SetInfo<ModelKey> difference = SetUtility.makeSetInfo(keys, currentKeys);

		RelationResultImpl result = null;

		if (adding) {
			result = new RelationResultImpl(difference.getIntersection(), difference.getCompliment());
		} else {
			result = new RelationResultImpl(difference.getCompliment(), difference.getIntersection());
		}

		return result;
	}

	@Override
	public RelationResult setRelation(Relation change) throws RelationChangeException, UnavailableLinkException {
		List<ModelKey> keys = change.getRelationKeys();
		List<ModelKey> currentKeys = this.converter.convertTo(this.collection);

		Set<ModelKey> hit = new HashSet<ModelKey>(currentKeys);
		Set<ModelKey> redundant = new HashSet<ModelKey>(keys);
		keys.retainAll(currentKeys);

		this.collection.clear();
		List<T> items = this.converter.convertFrom(keys);
		this.collection.addAll(items);

		return new RelationResultImpl(hit, redundant);
	}

	@Override
	public RelationResult clearRelations() {
		List<ModelKey> currentKeys = this.converter.convertTo(this.collection);

		this.collection.clear();

		return RelationResultImpl.hits(currentKeys);
	}

	@Override
	public String toString() {
		return "LinkCollectionImpl [linkInfo=" + this.linkInfo + ", collection=" + this.collection + ", converter="
		        + this.converter + "]";
	}

}
