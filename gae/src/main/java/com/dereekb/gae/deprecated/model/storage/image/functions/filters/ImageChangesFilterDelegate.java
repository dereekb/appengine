package com.thevisitcompany.gae.deprecated.model.storage.image.functions.filters;

import com.thevisitcompany.gae.deprecated.authentication.login.support.AbstractLoginSourceDependent;
import com.thevisitcompany.gae.deprecated.model.storage.image.Image;
import com.thevisitcompany.gae.model.crud.function.filters.CanCreateFilterDelegate;
import com.thevisitcompany.gae.model.crud.function.filters.CanDeleteFilterDelegate;
import com.thevisitcompany.gae.model.crud.function.filters.CanReadFilterDelegate;
import com.thevisitcompany.gae.model.crud.function.filters.CanUpdateFilterDelegate;

public class ImageChangesFilterDelegate extends AbstractLoginSourceDependent
        implements CanUpdateFilterDelegate<Image>, CanCreateFilterDelegate<Image>, CanDeleteFilterDelegate<Image>,
        CanReadFilterDelegate<Image> {

	@Override
	public boolean canCreate(Image object) {
		return true;
	}

	@Override
	public boolean canRead(Image object) {
		return true;
	}

	@Override
	public boolean canUpdate(Image object) {
		return true;
	}

	@Override
	public boolean canDelete(Image object) {
		return true;
	}

}
