package com.thevisitcompany.gae.deprecated.model.storage.image.functions.observer;

import java.util.List;

import com.thevisitcompany.gae.deprecated.model.storage.image.Image;
import com.thevisitcompany.gae.deprecated.model.storage.image.ImageType;
import com.thevisitcompany.gae.utilities.function.staged.StagedFunction;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionStage;
import com.thevisitcompany.gae.utilities.function.staged.observer.StagedFunctionObserver;

/**
 * Observer that sets the {@link ImageType} of images.
 * 
 * @author dereekb
 */
public class SetImageTypeObserver
        implements StagedFunctionObserver<Image> {

	private ImageType type;

	@Override
	public void functionHandlerCallback(StagedFunctionStage stage,
	                                    StagedFunction<Image, ?> handler) {
		List<Image> images = handler.getFunctionObjects();

		for (Image image : images) {
			image.setImageType(type);
		}
	}

	public ImageType getType() {
		return type;
	}

	public void setType(ImageType type) {
		this.type = type;
	}

}
