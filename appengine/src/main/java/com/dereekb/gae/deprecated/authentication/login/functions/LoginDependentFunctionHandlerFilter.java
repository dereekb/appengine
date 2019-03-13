package com.thevisitcompany.gae.deprecated.authentication.login.functions;

import com.thevisitcompany.gae.deprecated.authentication.login.support.LoginDependent;
import com.thevisitcompany.gae.utilities.function.staged.filter.StagedFunctionFilter;

public interface LoginDependentFunctionHandlerFilter<T> extends StagedFunctionFilter<T>, LoginDependent {

}
