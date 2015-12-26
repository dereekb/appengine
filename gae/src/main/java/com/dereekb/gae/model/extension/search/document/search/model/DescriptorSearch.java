package com.dereekb.gae.model.extension.search.document.search.model;

import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.AtomField;

/**
 * Search model for a {@link Descriptor}.
 *
 * @author dereekb
 */
public class DescriptorSearch {

	private String type;
	private String id;

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ExpressionBuilder make(String typeField,
	                              String idField) {
		ExpressionBuilder builder = null;

		if (this.type != null) {
			builder = new AtomField(typeField, this.type);
		}

		if (this.id != null) {
			AtomField id = new AtomField(idField, this.id);

			if (builder != null) {
				builder = id.and(builder);
			} else {
				builder = id;
			}
		}

		return builder;
	}

	@Override
	public String toString() {
		return "DescriptorSearch [type=" + this.type + ", id=" + this.id + "]";
	}

}
