package com.dereekb.gae.model.general.people.contact.generation;

import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.model.general.people.contact.ContactAddress;
import com.dereekb.gae.model.general.people.contact.ContactAddressType;
import com.dereekb.gae.utilities.misc.random.IntegerGenerator;

/**
 * {@link Generator} for {@link ContactAddress}.
 *
 * @author dereekb
 *
 */
public class ContactAddressGenerator extends AbstractGenerator<ContactAddress> {

	private static final IntegerGenerator TYPE_GENERATOR = new IntegerGenerator(0, 1);

	@Override
	public ContactAddress generate(GeneratorArg arg) {
		ContactAddress address;

		Integer key = TYPE_GENERATOR.generate(arg);

		switch (key) {
			case 0: // PHONE
				address = this.generatePhone(arg);
				break;
			case 1: // EMAIL
			default:
				address = this.generateEmail(arg);
				break;
		}

		return address;
	}

	public ContactAddress generatePhone(GeneratorArg arg) {
		ContactAddress address = new ContactAddress();

		address.setType(ContactAddressType.PHONE);
		address.setData("+12108603906");

		return address;
	}

	public ContactAddress generateEmail(GeneratorArg arg) {
		ContactAddress address = new ContactAddress();

		address.setType(ContactAddressType.EMAIL);
		address.setData("email@domain.com");

		return address;
	}

}
