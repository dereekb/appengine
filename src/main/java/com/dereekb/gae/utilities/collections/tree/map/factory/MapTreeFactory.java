package com.dereekb.gae.utilities.collections.tree.map.factory;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.utilities.collections.tree.map.MapTree;
import com.dereekb.gae.utilities.collections.tree.map.builder.MapTreeBuilder;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;

/**
 * Factory that builds a map tree node from a list of strings using a {@link MapTreeBuilder} and a list of tree strings that are marged
 * together.
 * 
 * @author dereekb
 *
 * @param <T>
 */
public class MapTreeFactory<T>
        implements Factory<MapTree<T>> {

	private MapTreeBuilder<T> builder;
	private List<String> treeStrings = Collections.emptyList();

	@Override
	public MapTree<T> make() throws FactoryMakeFailureException {

		if (builder == null) {
			throw new FactoryMakeFailureException("MapTreeFactory has no builder.");
		}

		MapTree<T> root = builder.decodeTrees(treeStrings);
		return root;
	}

	public MapTreeBuilder<T> getBuilder() {
		return builder;
	}

	public void setBuilder(MapTreeBuilder<T> builder) throws NullPointerException {
		if (builder == null) {
			throw new NullPointerException("Builder for MapTreeFactory cannot be null.");
		}

		this.builder = builder;
	}

	public List<String> getTreeStrings() {
		return treeStrings;
	}

	public void setTreeStrings(List<String> treeStrings) throws NullPointerException {
		if (treeStrings == null) {
			throw new NullPointerException("Tree strings cannot be null.");
		}

		this.treeStrings = treeStrings;
	}

}
