package com.dereekb.gae.extras.gen.test.model.foo.dto;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.extras.gen.test.model.foo.Foo;
import com.dereekb.gae.model.extension.links.descriptor.impl.dto.DescribedDatabaseModelData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * DTO of the {@link Foo} class.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FooData extends DescribedDatabaseModelData {

	private static final long serialVersionUID = 1L;

	private String name;

	private Integer number;

	private List<Integer> numberList;

	private Set<String> stringSet;

	public FooData() {}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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
		this.numberList = numberList;
	}

	public Set<String> getStringSet() {
		return this.stringSet;
	}

	public void setStringSet(Set<String> stringSet) {
		this.stringSet = stringSet;
	}

}
