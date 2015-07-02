package com.thevisitcompany.gae.deprecated.model.general;

import com.thevisitcompany.gae.model.extension.generation.AbstractGenerator;

public class ContactsGenerator extends AbstractGenerator<Contacts> {

	private String email = "contact@dn-solutions.com";
	private String phone = "+12101111111";

	@Override
	public Contacts generate() {

		Contacts contacts = new Contacts();

		contacts.setEmail(email);
		contacts.setPhone(phone);

		return contacts;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
