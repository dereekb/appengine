package com.thevisitcompany.gae.deprecated.authentication.login.functions;

import com.thevisitcompany.gae.deprecated.authentication.login.support.LoginSourceDependent;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionObject;
import com.thevisitcompany.gae.utilities.function.staged.filter.StagedFunctionObjectFilter;

public interface LoginDependentFunctionObjectHandlerFilter<T, W extends StagedFunctionObject<T>>
        extends StagedFunctionObjectFilter<T, W>, LoginSourceDependent {

}
