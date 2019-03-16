package com.thevisitcompany.gae.deprecated.model.general;

import com.thevisitcompany.gae.model.extension.generation.AbstractGenerator;

public class MediaGenerator extends AbstractGenerator<Media> {

	@Override
	public Media generate() {
		Media links = new Media();

		links.setFacebook("visitapp");
		links.setGoogle("107661756513647214258");
		links.setTwitter("@thevisitapp");

		return links;
	}

}
