package com.dereekb.gae.utilities.data.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.utilities.data.ObjectMapperUtility;
import com.dereekb.gae.utilities.data.ObjectMapperUtilityBuilder;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link ObjectMapperUtilityBuilder} implementation.
 *
 * @author dereekb
 *
 */
public class ObjectMapperUtilityBuilderImpl
        implements ObjectMapperUtilityBuilder {

	public static final ObjectMapperUtilityBuilder SINGLETON = new ObjectMapperUtilityBuilderImpl();

	private final ObjectMapper mapper;

	public ObjectMapperUtilityBuilderImpl() {
		this(new ObjectMapper());
	}

	public ObjectMapperUtilityBuilderImpl(ObjectMapper mapper) throws IllegalArgumentException {
		if (mapper == null) {
			throw new IllegalArgumentException("mapperUtility cannot be null.");
		}

		this.mapper = mapper;
	}

	public static ObjectMapperUtilityBuilder builder(ObjectMapper mapper) throws IllegalArgumentException {
		return new ObjectMapperUtilityBuilderImpl(mapper);
	}

	// MARK: Builder
	@Override
	public ObjectMapperUtility make() {
		return new ObjectMapperUtilityBuilderInstance().make();
	}

	@Override
	public ObjectMapperUtilityBuilder nullSafe() {
		return new ObjectMapperUtilityBuilderInstance().nullSafe();
	}

	@Override
	public ObjectMapperUtilityBuilder nullSafe(boolean safe) {
		return new ObjectMapperUtilityBuilderInstance().nullSafe(safe);
	}

	// MARK: Utility
	protected class ObjectMapperUtilityBuilderInstance
	        implements ObjectMapperUtilityBuilder {

		private boolean nullSafe = false;

		public ObjectMapper overrideMapper;

		public ObjectMapperUtilityBuilderInstance() throws IllegalArgumentException {}

		public ObjectMapperUtilityBuilderInstance(ObjectMapperUtility instance) throws IllegalArgumentException {
			this.overrideMapper = instance.getMapper();
			this.nullSafe = instance.isNullSafe();
		}

		public boolean isNullSafe() {
			return this.nullSafe;
		}

		public ObjectMapper getOverrideMapper() {
			return this.overrideMapper;
		}

		public ObjectMapperUtilityBuilderInstance setOverrideMapper(ObjectMapper overrideMapper) {
			this.overrideMapper = overrideMapper;
			return this;
		}

		// MARK: ObjectMapperUtilityBuilder
		@Override
		public ObjectMapperUtilityBuilder nullSafe() {
			return this.nullSafe(true);
		}

		@Override
		public ObjectMapperUtilityBuilder nullSafe(boolean safe) {
			this.nullSafe = safe;
			return this;
		}

		@Override
		public ObjectMapperUtility make() {
			return new ObjectMapperUtilityInstance(this);
		}

	}

	private class ObjectMapperUtilityInstance
	        implements ObjectMapperUtility {

		private boolean nullSafe = false;

		public final ObjectMapper mapper;

		public ObjectMapperUtilityInstance(ObjectMapperUtilityBuilderInstance builder) {
			this(builder.overrideMapper);
			this.setNullSafe(builder.isNullSafe());
		}

		public ObjectMapperUtilityInstance(ObjectMapper overrideMapper) throws IllegalArgumentException {
			if (overrideMapper == null) {
				this.mapper = ObjectMapperUtilityBuilderImpl.this.mapper;
			} else {
				this.mapper = overrideMapper;
			}
		}

		@Override
		public boolean isNullSafe() {
			return this.nullSafe;
		}

		public void setNullSafe(boolean nullSafe) {
			this.nullSafe = nullSafe;
		}

		@Override
		public ObjectMapper getMapper() {
			return this.mapper;
		}

		// MARK: Utility
		@Override
		public <X> Set<X> safeMapArrayToSet(JsonNode jsonNode,
		                                    Class<X> type) {
			try {
				return this.mapArrayToSet(jsonNode, type);
			} catch (IOException e) {
				return Collections.emptySet();
			}
		}

		@Override
		public <X> List<X> safeMapArrayToList(JsonNode jsonNode,
		                                      Class<X> type) {
			try {
				return this.mapArrayToList(jsonNode, type);
			} catch (IOException e) {
				return Collections.emptyList();
			}
		}

		@Override
		public <X> X safeMap(JsonNode jsonNode,
		                     Class<X> type) {
			try {
				return this.map(jsonNode, type);
			} catch (IOException e) {
				return null;
			}
		}

		@Override
		public <X> Set<X> mapArrayToSet(JsonNode jsonNode,
		                                Class<X> type)
		        throws IOException {
			return SetUtility.copy(this.mapArrayToList(jsonNode, type));
		}

		@Override
		public <X> List<X> mapArrayToList(JsonNode jsonNode,
		                                  Class<X> type)
		        throws IOException {
			try {
				JavaType javaType = this.mapper.getTypeFactory().constructCollectionType(List.class, type);
				return this.mapper.readerFor(javaType).readValue(jsonNode);
			} catch (NullPointerException e) {
				this.handleNullPointerException(e);
				return Collections.emptyList();
			}
		}

		@Override
		public <X> X map(JsonNode jsonNode,
		                 Class<X> type)
		        throws IOException {
			try {
				return this.mapper.treeToValue(jsonNode, type);
			} catch (NullPointerException e) {
				this.handleNullPointerException(e);
				return null;
			}
		}

		private void handleNullPointerException(NullPointerException e) throws NullPointerException {
			if (!this.nullSafe) {
				throw e;
			}
		}

	}

	@Override
	public String toString() {
		return "ObjectMapperUtilityBuilderImpl [mapper=" + this.mapper + "]";
	}

}
