package com.dereekb.gae.model.extension.links.deprecated.service.unlink;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.links.deprecated.functions.BidirectionalLinkerFunction;
import com.dereekb.gae.model.extension.links.deprecated.functions.LinksAction;
import com.dereekb.gae.model.extension.links.deprecated.service.BidirectionalLinkServiceComponent;
import com.dereekb.gae.model.extension.links.deprecated.service.LinksServiceModelRequest;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * Automate an unlinking process by retrieving the keys from the targets
 * that it should unlink from, then uses a {@link BidirectionalLinkerFunction}
 * to unlink elements.
 *
 * @author dereekb
 *
 * @param <T>
 *            Target Type
 */
public class BidirectionalUnlinkerComponent<T extends UniqueModel> {

	/**
	 * Whether or not to make sure all elements exist before unlinking.
	 */
	private Boolean safe = false;

	private Factory<BidirectionalLinkerFunction<T, ?>> linkerFactory;
	private BidirectionalUnlinkerComponentDelegate<T> delegate;
	private String type;

	public BidirectionalUnlinkerComponent() {}

	public BidirectionalUnlinkerComponent(Factory<BidirectionalLinkerFunction<T, ?>> linkerFactory,
	        BidirectionalUnlinkerComponentDelegate<T> delegate,
	        String type) {
		this.linkerFactory = linkerFactory;
		this.delegate = delegate;
		this.type = type;
	}

	public List<T> unlink(Iterable<T> objects) {
		List<LinksServiceModelRequest<T>> requests = this.makeUnlinkRequests(objects);

		BidirectionalLinkerFunction<T, ?> linker = this.makeLinker();
		BidirectionalLinkServiceComponent<T> component = new BidirectionalLinkServiceComponent<T>(null, linker);
		return component.linksChangeWithModelRequests(requests);
	}

	public List<LinksServiceModelRequest<T>> makeUnlinkRequests(Iterable<T> objects) {
		List<LinksServiceModelRequest<T>> requests = new ArrayList<LinksServiceModelRequest<T>>();

		for (T object : objects) {
			Collection<ModelKey> linkIdentifers = this.delegate.keysToUnlinkFrom(this.type, object);

			if (linkIdentifers.isEmpty() == false) {
				LinksServiceModelRequest<T> request = new LinksServiceModelRequest<T>(object, linkIdentifers,
				        this.type, LinksAction.UNLINK);
				requests.add(request);
			}
		}

		return requests;
	}

	public BidirectionalLinkerFunction<T, ?> makeLinker() {
		BidirectionalLinkerFunction<T, ?> linker = this.linkerFactory.make();

		if (this.safe != null) {
			linker.setSafe(this.safe);
		}

		return linker;
	}

	public BidirectionalUnlinkerComponentDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(BidirectionalUnlinkerComponentDelegate<T> delegate) {
		this.delegate = delegate;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getSafe() {
		return this.safe;
	}

	public void setSafe(Boolean safe) {
		this.safe = safe;
	}

	public Factory<BidirectionalLinkerFunction<T, ?>> getLinkerFactory() {
		return this.linkerFactory;
	}

	public void setLinkerFactory(Factory<BidirectionalLinkerFunction<T, ?>> linkerFactory) {
		this.linkerFactory = linkerFactory;
	}
}
