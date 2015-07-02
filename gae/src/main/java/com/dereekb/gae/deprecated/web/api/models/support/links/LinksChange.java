package com.thevisitcompany.gae.deprecated.web.api.models.support.links;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Used in PUT requests to change links on the server.
 *
 * @author dereekb
 */
public class LinksChange<K> {

	@NotEmpty
	private String type;

	/**
	 * Identifiers of the target items to change.
	 *
	 * These should be the models/type that the request is being set to, so a
	 * URI of "/place/..." will refer to Place model targets.
	 */
	@NotEmpty
	private List<K> targets;

	/**
	 * Identifiers of the objects being linked to the targets.
	 *
	 * Theses should be the models/type that {@link LinksChange} specifies in
	 * the type field.
	 */
	@NotEmpty
	private List<K> identifiers;

	public LinksChange() {}

	public LinksChange(String type, K target, List<K> identifiers) {
		this.type = type;
		this.setTarget(target);
		this.setIdentifiers(identifiers);
	}

	public LinksChange(String type, List<K> targets, K identifier) {
		this.type = type;
		this.setTargets(targets);
		this.setIdentifier(identifier);
	}

	public LinksChange(String type, List<K> targets, List<K> identifiers) {
		this.type = type;
		this.setTargets(targets);
		this.setIdentifiers(identifiers);
	}

	/**
	 * Returns the type of the identifiers we are attaching to.
	 * @return
	 */
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the first identifier.
	 *
	 * @return
	 */
	public K getIdentifier() {
		return this.identifiers.get(0);
	}

	/**
	 * Returns the list of identifiers we want to attach to the target(s).
	 *
	 * @return
	 */
	public List<K> getIdentifiers() {
		return this.identifiers;
	}

	public void setIdentifier(K identifier) {
		this.identifiers = new ArrayList<K>();
		this.identifiers.add(identifier);
	}

	public void setIdentifiers(List<K> identifiers) {
		this.identifiers = identifiers;
	}

	/**
	 * Returns the first target.
	 *
	 * @return
	 */
	public K getTarget() {
		return this.targets.get(0);
	}

	/**
	 * Returns the list of identifiers of the target objects.
	 *
	 * @return
	 */
	public List<K> getTargets() {
		return this.targets;
	}

	public void setTarget(K target) {
		this.targets = new ArrayList<K>();
		this.targets.add(target);
	}

	public void setTargets(List<K> targets) {
		this.targets = targets;
	}

	public static <K> List<LinksChange<K>> filterChangesWithTypes(List<LinksChange<K>> changes,
	                                                              String... types) {
		List<LinksChange<K>> filteredChanges = new ArrayList<LinksChange<K>>();
		List<String> typesList = Arrays.asList(types);
		Set<String> typesSet = new HashSet<String>(typesList);

		for (LinksChange<K> change : changes) {
			String type = change.getType();

			if (typesSet.contains(type)) {
				filteredChanges.add(change);
			}
		}

		return filteredChanges;
	}
}
