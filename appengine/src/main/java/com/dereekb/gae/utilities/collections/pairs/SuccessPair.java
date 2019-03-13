package com.dereekb.gae.utilities.collections.pairs;

import com.dereekb.gae.utilities.misc.success.SuccessModel;

/**
 * {@link ResultPair} with a boolean as a result.
 * 
 * @author dereekb
 *
 * @param <S>
 *            source model type
 */
public interface SuccessPair<S>
        extends ResultPair<S, Boolean>, SuccessModel {}
