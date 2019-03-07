package com.dereekb.gae.extras.gen.test.model.foo;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.descriptor.impl.DescribedDatabaseModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyGenerationType;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyInfo;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfDefault;
import com.googlecode.objectify.condition.IfEmpty;
import com.googlecode.objectify.condition.IfNotDefault;
import com.googlecode.objectify.condition.IfNotEmpty;

/**
 * Test model used for unit/integration testing of various components.
 *
 * @author dereekb
 *
 */
@Cache
@Entity
@ModelKeyInfo(value = ModelKeyType.NUMBER, generation = ModelKeyGenerationType.AUTOMATIC)
public class Foo extends DescribedDatabaseModel
        implements ObjectifyModel<Foo> {

	private static final long serialVersionUID = 1L;

	/**
	 * Database identifier.
	 */
	@Id
	private Long identifier;

	/**
	 * Creation date of the login.
	 */
	@Index
	private Date date = new Date();

	/**
	 * Number group identifier
	 */
	@Index({ IfNotDefault.class })
	@IgnoreSave({ IfDefault.class })
	private Integer number = 0;

	/**
	 * Number list
	 */
	@Index({ IfNotEmpty.class })
	@IgnoreSave({ IfEmpty.class })
	private List<Integer> numberList;

	/**
	 * String set
	 */
	@Index({ IfNotEmpty.class })
	@IgnoreSave({ IfEmpty.class })
	private Set<String> stringSet;

	public Foo() {}

	public Foo(Long identifier) {
		this.identifier = identifier;
	}

	public Long getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public List<Integer> getNumberList() {
		return this.numberList;
	}

	public void setNumberList(List<Integer> numberList) {
		this.numberList = ListUtility.newList(numberList);
	}

	public Set<String> getStringSet() {
		return this.stringSet;
	}

	public void setStringSet(Set<String> stringSet) {
		this.stringSet = SetUtility.newHashSet(stringSet);
	}

	// Unique Model
	@Override
	public ModelKey getModelKey() {
		return ModelKey.safe(this.identifier);
	}

	@Override
	public void setModelKey(ModelKey key) {
		this.identifier = ModelKey.readIdentifier(key);
	}

	// Database Model
	@Override
	protected Object getDatabaseIdentifier() {
		return this.identifier;
	}

	// Objectify Model
	@Override
	public Key<Foo> getObjectifyKey() {
		return Key.create(Foo.class, this.identifier);
	}

}
