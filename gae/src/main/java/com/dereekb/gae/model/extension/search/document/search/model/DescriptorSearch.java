package com.dereekb.gae.model.extension.search.document.search.model;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.AtomField;

/**
 * Search model for a {@link Descriptor}.
 *
 * @author dereekb
 */
public class DescriptorSearch {

	private static final String SPLITTER = ",";

	private String type;
	private String id;

	public DescriptorSearch(String type) {
		this.setType(type);
	}

	public DescriptorSearch(String type, String id) {
		this.setType(type);
		this.setId(id);
	}

	/**
	 * Creates a {@link DescriptorSearch} from the input string.
	 * <p>
	 * Format: TYPE(String),ID(String)
	 *
	 * @param descriptorString
	 * @return {@link DescriptorString} or {@code null} if nothing is input.
	 * @throws IllegalArgumentException
	 */
	public static DescriptorSearch fromString(String descriptorString) throws IllegalArgumentException {
		DescriptorSearch descriptor = null;

		if (descriptorString != null && descriptorString.isEmpty() == false) {
			try {
				String[] split = descriptorString.split(SPLITTER);

				String type = null;
				String id = null;

				switch (split.length) {
					default:
					case 2:
						id = split[1];
					case 1:
						type = split[0];
						break;
				}

				descriptor = new DescriptorSearch(type, id);
			} catch (Exception e) {
				throw new IllegalArgumentException("Could not create descriptor.", e);
			}
		}

		return descriptor;
	}

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

	public ExpressionBuilder make() {
		return this.make(ModelDocumentBuilderUtility.DESCRIPTOR_TYPE_FIELD,
		        ModelDocumentBuilderUtility.DESCRIPTOR_ID_FIELD);
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
