package com.dereekb.gae.client.api.model.crud.utility;

/**
 * Serializer that can serialize models, keys, and DTOs.
 * 
 * @author dereekb
 * 
 * @param <T>
 *            model type
 * @param <O>
 *            model dto type
 */
public interface JsonModelResultsSerializer<T, O>
        extends JsonFullModelResultsSerializer<T>, JsonModelDtoResultsSerializer<O>, JsonKeyResultsSerializer {

}
