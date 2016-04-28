package com.dereekb.gae.model.extension.search.document.search.model;

import com.dereekb.gae.model.general.people.contact.search.ContactAddressSetDocumentBuilderUtility;
import com.dereekb.gae.model.general.time.search.WeekTimeDocumentBuilderUtility.FieldFormatter;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.TextField;

/**
 * Search component for {@link ContactAddressSetDocumentBuilderUtility}.
 *
 * @author dereekb
 */
public class ContactAddressSearch {

	private static final String SPLITTER = ",";

	private String address;
	private String type;

	public ContactAddressSearch(String address) {
		this.setAddress(address);
	}

	public ContactAddressSearch(String address, String type) {
		this(address);
		this.setType(type);
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Creates a {@link ContactAddressSearch} from the input string.
	 * <p>
	 * Format: ADDRESS(String), TYPE?(String)
	 *
	 * @param addressString
	 * @return {@link ContactAddressSearch} or {@code null} if nothing is input.
	 * @throws IllegalArgumentException
	 */
	public static ContactAddressSearch fromString(String addressString) throws IllegalArgumentException {
		ContactAddressSearch search = null;

		if (addressString != null && addressString.isEmpty() == false) {
			try {
				String[] split = addressString.split(SPLITTER);

				String address = null;
				String type = null;

				switch (split.length) {
					default:
					case 2:
						type = split[1];
					case 1:
						address = split[0];
						break;
				}

				search = new ContactAddressSearch(address, type);
			} catch (Exception e) {
				throw new IllegalArgumentException("Could not create contact search.", e);
			}
		}

		return search;
	}

	public ExpressionBuilder make(String field) {
		FieldFormatter formatter = new FieldFormatter(field);
		ExpressionBuilder builder;

		if (this.address != null) {
			builder = new TextField(formatter.daysField(), this.address);

			if (this.type != null) {
				builder = builder.and(new TextField(formatter.daysField(), this.address));
			}
		} else {
			builder = null;
		}

		return builder;
	}

	@Override
	public String toString() {
		return "ContactAddressSearch [address=" + this.address + ", type=" + this.type + "]";
	}

}
