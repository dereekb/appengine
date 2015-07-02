package com.thevisitcompany.gae.test.deprecated.models.support.links;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedModel;
import com.thevisitcompany.gae.model.extension.links.functions.BidirectionalLinkerFunctionTypeDelegate;
import com.thevisitcompany.gae.model.extension.links.functions.LinksAction;
import com.thevisitcompany.gae.model.extension.links.functions.LinksHandler;
import com.thevisitcompany.gae.model.extension.links.functions.LinksMethod;
import com.thevisitcompany.gae.model.extension.links.functions.LinksPair;
import com.thevisitcompany.gae.server.datastore.GetterSetter;

public abstract class AbstractLinkerFunctionTests {

	protected static class LinkerObject
	        implements KeyedModel<Long> {

		private Long identifier;
		private Set<Long> setA;
		private Set<Long> setB;

		public LinkerObject() {
			this.setSetA(new HashSet<Long>());
			this.setSetB(new HashSet<Long>());
		}

		public LinkerObject(Long identifier) {
			this();
			this.identifier = identifier;
		}

		public Set<Long> getSetA() {
			return setA;
		}

		public void setSetA(Set<Long> setA) {
			this.setA = setA;
		}

		public Set<Long> getSetB() {
			return setB;
		}

		public void setSetB(Set<Long> setB) {
			this.setB = setB;
		}

		@Override
		public Long getId() {
			return this.identifier;
		}

	}

	protected static class LinkerObjectLinkHandler
	        implements LinksHandler<LinkerObject>, BidirectionalLinkerFunctionTypeDelegate {

		@LinksMethod("a")
		public void linkASet(LinksPair<LinkerObject, Long> pair) {
			LinkerObject object = pair.getTarget();
			Set<Long> aSet = object.getSetA();
			aSet.addAll(pair.getLinks());
		}

		@LinksMethod(value = "a", action = LinksAction.UNLINK)
		public void unlinkASet(LinksPair<LinkerObject, Long> pair) {
			LinkerObject object = pair.getTarget();
			Set<Long> aSet = object.getSetA();
			aSet.removeAll(pair.getLinks());
		}

		@LinksMethod("b")
		public void linkBSet(LinksPair<LinkerObject, Long> pair) {
			LinkerObject object = pair.getTarget();
			Set<Long> bSet = object.getSetB();
			bSet.addAll(pair.getLinks());
		}

		@LinksMethod(value = "b", action = LinksAction.UNLINK)
		public void unlinkBSet(LinksPair<LinkerObject, Long> pair) {
			LinkerObject object = pair.getTarget();
			Set<Long> bSet = object.getSetB();
			bSet.removeAll(pair.getLinks());
		}

		@Override
		public String linkerTypeForType(String type) {

			String linkerType = "";

			switch (type) {
				case "a": {
					linkerType = "b";
				}
					break;
				case "b": {
					linkerType = "a";
				}
					break;
			}

			return linkerType;
		}

	}

	protected static class LinkerObjectGetterSetter
	        implements GetterSetter<LinkerObject, Long> {

		List<LinkerObject> savedObjects = new ArrayList<LinkerObject>();

		@Override
		public boolean exists(Long identifier) {
			return true;
		}

		@Override
		public boolean allExist(Iterable<Long> identifiers) {
			return true;
		}

		@Override
		public Set<Long> exists(Iterable<Long> identifiers) {
			return null;
		}

		@Override
		public LinkerObject get(Long identifier) {
			return new LinkerObject(identifier);
		}

		@Override
		public LinkerObject get(LinkerObject model) {
			return new LinkerObject(model.getId());
		}

		@Override
		public List<LinkerObject> get(Iterable<LinkerObject> models) {
			List<LinkerObject> objects = new ArrayList<LinkerObject>();

			for (LinkerObject model : models) {
				objects.add(new LinkerObject(model.getId()));
			}

			return objects;
		}

		@Override
		public List<LinkerObject> getWithIds(Iterable<Long> identifiers) {
			List<LinkerObject> objects = new ArrayList<LinkerObject>();

			for (Long identifier : identifiers) {
				objects.add(new LinkerObject(identifier));
			}

			return objects;
		}

		@Override
		public void save(LinkerObject entity,
		                 boolean async) {
			savedObjects.add(entity);
		}

		@Override
		public void save(Iterable<LinkerObject> entities,
		                 boolean async) {
			for (LinkerObject entity : entities) {
				savedObjects.add(entity);
			}
		}

		@Override
		public void delete(LinkerObject entity,
		                   boolean async) {}

		@Override
		public void delete(Iterable<LinkerObject> entities,
		                   boolean async) {}

		public List<LinkerObject> getSavedObjects() {
			return savedObjects;
		}

		public void setSavedObjects(List<LinkerObject> savedObjects) {
			this.savedObjects = savedObjects;
		}

	}

}
