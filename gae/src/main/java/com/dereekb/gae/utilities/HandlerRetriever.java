package com.dereekb.gae.utilities;

@Deprecated
public class HandlerRetriever {

	/*
	private PublishHandler retrieveHandler(){

		PublishHandler handler = null;
		
		try {
				
			Class<?> objectType = this.object.getClass();
			PublishHandlerClass validatorAnnotation = objectType.getAnnotation(PublishHandlerClass.class);
			Class<?> handlerType = validatorAnnotation.value();
			handler = (PublishHandler)handlerType.newInstance();
			
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Error while initializing handler for object '" + object + "'.");
		} catch (NullPointerException e){
			throw new RuntimeException("No publish annotation for object '" + object + "'.");
		}

		return handler;
	}
	 */
}
