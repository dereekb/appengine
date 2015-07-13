package com.dereekb.gae.model.extension.links.descriptor.generator;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.links.descriptor.DescriptorImpl;
import com.dereekb.gae.utilities.misc.random.StringLongGenerator;

/**
 * Used for generating {@link Descriptor} instances. Uses a {@link String}
 * {@link Generator} for retrieving types.
 *
 * @author dereekb
 *
 */
public class DescriptorGenerator extends AbstractGenerator<Descriptor> {

	private Generator<String> typeGenerator;
	private Generator<String> idGenerator = StringLongGenerator.GENERATOR;

	public DescriptorGenerator(Generator<String> typeGenerator) {
		this.typeGenerator = typeGenerator;
	}

	public DescriptorGenerator(Generator<String> typeGenerator, Generator<String> idGenerator) {
		this.typeGenerator = typeGenerator;
		this.idGenerator = idGenerator;
	}

	public Generator<String> getTypeGenerator() {
		return this.typeGenerator;
	}

	public void setTypeGenerator(Generator<String> typeGenerator) {
		this.typeGenerator = typeGenerator;
	}

	public Generator<String> getIdGenerator() {
		return this.idGenerator;
	}

	public void setIdGenerator(Generator<String> idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Override
	public Descriptor generate(GeneratorArg arg) {
		String type = this.typeGenerator.generate(arg);
		String id = this.idGenerator.generate(arg);

		DescriptorImpl descriptor = new DescriptorImpl(type, id);
		return descriptor;
	}

	@Override
	public String toString() {
		return "DescriptorGenerator [typeGenerator=" + this.typeGenerator + ", idGenerator=" + this.idGenerator + "]";
	}

}
