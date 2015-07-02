package com.thevisitcompany.gae.deprecated.model.general;

import com.thevisitcompany.gae.model.extension.generation.AbstractGenerator;

public final class WebsiteGenerator extends AbstractGenerator<Website> {

	@Override
	public Website generate() {
		Integer value = this.randomPositiveInt();
		Website website = new Website("Website: " + value, "http://www.website" + value + ".com");
		return website;
	}

}
