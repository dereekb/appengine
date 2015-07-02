package com.thevisitcompany.gae.deprecated.model.general;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.thevisitcompany.gae.model.general.validation.Phone;

/**
 * Contains contact information.
 *
 * The phone-number is based on the E.164 standard.
 *
 * @see {@link http://en.wikipedia.org/wiki/E.164}
 */
@Deprecated
public class Contacts {

	@Email
	@Size(max = 128)
	private String email;

	@Phone
	@Size(min = 12, max = 16)
	private String phone;

	public Contacts() {}

	public Contacts(String email, String phone) {
		this.email = email;
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Contacts [email=" + this.email + ", phone=" + this.phone + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
		result = prime * result + ((this.phone == null) ? 0 : this.phone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
	        return true;
        }
		if (obj == null) {
	        return false;
        }
		if (this.getClass() != obj.getClass()) {
	        return false;
        }
		Contacts other = (Contacts) obj;
		if (this.email == null) {
			if (other.email != null) {
	            return false;
            }
		} else if (!this.email.equals(other.email)) {
	        return false;
        }
		if (this.phone == null) {
			if (other.phone != null) {
	            return false;
            }
		} else if (!this.phone.equals(other.phone)) {
	        return false;
        }
		return true;
	}

}
