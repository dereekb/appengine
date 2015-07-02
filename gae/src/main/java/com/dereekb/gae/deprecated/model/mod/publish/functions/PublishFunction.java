package com.thevisitcompany.gae.deprecated.model.mod.publish.functions;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.thevisitcompany.gae.deprecated.model.mod.publish.KeyedPublishableModel;
import com.thevisitcompany.gae.deprecated.model.mod.publish.utility.PublishEditor;
import com.thevisitcompany.gae.utilities.collections.map.HashMapWithList;
import com.thevisitcompany.gae.utilities.function.staged.filter.FilteredStagedFunction;

public class PublishFunction<T extends KeyedPublishableModel<Long>> extends FilteredStagedFunction<T, PublishPair<T>> {

	private PublishEditor<T> editor;

	public PublishFunction() {}

	public PublishFunction(PublishEditor<T> editor) {
		this.setEditor(editor);
	}

	@Override
	protected void doFunction() {
		Collection<PublishPair<T>> pairs = this.getWorkingObjects();
		HashMapWithList<PublishAction, PublishPair<T>> map = PublishPair.getActionPairsMap(pairs);

		this.makePublishRequests(map.getObjects(PublishAction.PUBLISH_REQUEST));
		this.makeUnpublishRequests(map.getObjects(PublishAction.UNPUBLISH_REQUEST));
		this.publish(map.getObjects(PublishAction.PUBLISH));
		this.unpublish(map.getObjects(PublishAction.UNPUBLISH));
	}

	private void makePublishRequests(List<PublishPair<T>> pairs) {
		if (pairs.size() > 0) {
			List<T> models = PublishPair.getSources(pairs);
			List<T> successful = this.editor.requestPublish(models);
			this.setSuccessfulChanges(successful, pairs);
		}
	}

	private void makeUnpublishRequests(List<PublishPair<T>> pairs) {
		if (pairs.size() > 0) {
			List<T> models = PublishPair.getSources(pairs);
			List<T> successful = this.editor.requestUnpublish(models);
			this.setSuccessfulChanges(successful, pairs);
		}
	}

	private void publish(List<PublishPair<T>> pairs) {
		if (pairs.size() > 0) {
			List<T> models = PublishPair.getSources(pairs);
			List<T> successful = this.editor.publish(models);
			this.setSuccessfulChanges(successful, pairs);
		}
	}

	private void unpublish(List<PublishPair<T>> pairs) {
		if (pairs.size() > 0) {
			List<T> models = PublishPair.getSources(pairs);
			List<T> successful = this.editor.unpublish(models);
			this.setSuccessfulChanges(successful, pairs);
		}
	}

	private void setSuccessfulChanges(Collection<T> successful,
	                                  Iterable<PublishPair<T>> pairs) {
		Set<T> successSet = new HashSet<T>(successful);
		for (PublishPair<T> pair : pairs) {
			boolean success = successSet.contains(pair.getSource());
			pair.setSuccessful(success);
		}
	}

	public PublishEditor<T> getEditor() {
		return editor;
	}

	public void setEditor(PublishEditor<T> editor) {
		this.editor = editor;
	}

}
