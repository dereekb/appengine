package com.thevisitcompany.gae.deprecated.model.general;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Container of social media.
 *
 * Contains relevant relative resources for each instead of the full URI.
 *
 * @author dereekb
 * @deprecated Is too limiting. Merge with {@link Website}.
 */
@Deprecated
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Media {

	@Size(min = 0, max = 15)
	private String twitter;

	@Size(min = 0, max = 50)
	private String facebook;

	@Size(min = 0, max = 60)
	private String google;

	public Media() {};

	public Media(String twitter, String facebook) {
		this.twitter = twitter;
		this.facebook = facebook;
	};

	public String getTwitter() {
		return this.twitter;
	}

	public void setTwitter(String twitter) {
		if (twitter != null && twitter.startsWith("@")) {
			twitter = twitter.substring(1);
		}

		this.twitter = twitter;
	}

	public String getFacebook() {
		return this.facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getGoogle() {
		return this.google;
	}

	public void setGoogle(String google) {
		this.google = google;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.facebook == null) ? 0 : this.facebook.hashCode());
		result = prime * result + ((this.google == null) ? 0 : this.google.hashCode());
		result = prime * result + ((this.twitter == null) ? 0 : this.twitter.hashCode());
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
		Media other = (Media) obj;
		if (this.facebook == null) {
			if (other.facebook != null) {
	            return false;
            }
		} else if (!this.facebook.equals(other.facebook)) {
	        return false;
        }
		if (this.google == null) {
			if (other.google != null) {
	            return false;
            }
		} else if (!this.google.equals(other.google)) {
	        return false;
        }
		if (this.twitter == null) {
			if (other.twitter != null) {
	            return false;
            }
		} else if (!this.twitter.equals(other.twitter)) {
	        return false;
        }
		return true;
	}

	@Override
	public String toString() {
		return "Media [twitter=" + this.twitter + ", facebook=" + this.facebook + ", google=" + this.google + "]";
	}

}
