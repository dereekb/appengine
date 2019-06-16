package com.dereekb.gae.model.general.people.contact.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.deprecated.search.document.index.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.model.general.people.contact.ContactAddress;
import com.dereekb.gae.model.general.people.contact.ContactAddressType;
import com.dereekb.gae.utilities.misc.SubFieldNameFormatter;
import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.google.appengine.api.search.Document.Builder;

/**
 * Used for embedding a {@link Set} of {@link ContactAddress} info into a search
 * document.
 *
 * @author dereekb
 *
 */
public class ContactAddressSetDocumentBuilderUtility {

	public static final ContactAddressSetDocumentBuilderUtility UTILITY = new ContactAddressSetDocumentBuilderUtility();

	public static final String SPACER = " ";

	public static final String DEFAULT_FIELD_NAME = "contacts";

	public static final String ADDRESSES_FIELD = "addresses";
	public static final String TYPES_FIELD = "types";

	private String spacer = SPACER;

	public String getDaysSplitter() {
		return this.spacer;
	}

	public void setDaysSplitter(String daysSplitter) {
		this.spacer = daysSplitter;
	}

	// MARK: Make
	public Instance make() {
		return new Instance();
	};

	public Instance make(Collection<ContactAddress> contactAddressSet) {
		return new Instance(contactAddressSet);
	};

	// TODO: Add Extended class with more information such as an open all day
	// boolean.

	public static class FieldFormatter extends SubFieldNameFormatter {

		public FieldFormatter() {
			this(DEFAULT_FIELD_NAME);
		}

		public FieldFormatter(String field) {
			super(field);
		}

		public String addressesField() {
			return this.format(ADDRESSES_FIELD);
		}

		public String typesField() {
			return this.format(TYPES_FIELD);
		}

	}

	public class Instance {

		private FieldFormatter formatter = new FieldFormatter();

		private String types;
		private String addresses;

		private Instance() {}

		private Instance(Collection<ContactAddress> contactAddresses) {
			List<String> typeStrings = new ArrayList<String>();
			List<String> dataList = new ArrayList<String>();

			for (ContactAddress address : contactAddresses) {
				ContactAddressType type = address.getType();
				String data = address.getData();

				typeStrings.add(type.getType());
				dataList.add(data);
			}

			Joiner joiner = Joiner.on(ContactAddressSetDocumentBuilderUtility.this.spacer).skipNulls();
			this.addresses = joiner.join(dataList);
			this.types = joiner.join(typeStrings);
		}

		public FieldFormatter getFieldNamer() {
			return this.formatter;
		}

		public void setFieldFormat(String format) {
			this.formatter.setFieldFormat(format);
		}

		// MARK: Attach
		public void attach(Builder builder) {

			// Addresses
			String addressesName = this.formatter.addressesField();
			SearchDocumentBuilderUtility.addText(addressesName, this.addresses, builder);

			// Types
			String typesName = this.formatter.typesField();
			SearchDocumentBuilderUtility.addText(typesName, this.types, builder);

		}

	}

	@Override
	public String toString() {
		return "ContactAddressSetDocumentBuilderUtility [spacer=" + this.spacer + "]";
	}

}
