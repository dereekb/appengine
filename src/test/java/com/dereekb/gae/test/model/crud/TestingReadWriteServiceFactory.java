package com.dereekb.gae.test.model.crud;

import java.util.Map;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.factory.Factory;

@SuppressWarnings("unused")
@Deprecated
public class TestingReadWriteServiceFactory {
	
	public <T extends MutableUniqueModel> Instance<T> makeInstance(Class<T> type) {
		return Instance.withClass(type);
	}

	public static class Instance<T extends MutableUniqueModel> {
		
		private Factory<T> factory;
		private Map<ModelKey, T> map;
		
		private ReadService<T> readService;
		
		public static <T extends MutableUniqueModel> Instance<T> withClass(Class<T> type) {
			return null;
		}

		
		public ReadService<T> getReadService() {
			if (this.readService == null) {
				this.readService = this.makeReadService();
			}
			
			return this.readService;
		}

		private ReadService<T> makeReadService() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
}
