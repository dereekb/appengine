package com.dereekb.gae.test.utility.collection.chain;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.utilities.collections.chain.ValuesChain;

public class ChainTests {

	@Test
	public void testChain() {
		ValuesChain<String> stringChain = new ValuesChain<String>("a");
		stringChain.chain("b").chain("c").chain("d").chain("e");

		String string = stringChain.toString(",");
		Assert.assertTrue(string.equals("a,b,c,d,e"));
	}

	@Test
	public void testChainIterator() {
		ValuesChain<String> stringChain = new ValuesChain<String>("a");
		stringChain.chain("b").chain("c").chain("d").chain("e");

		Iterator<ValuesChain<String>> iterator = stringChain.iterator();
		ValuesChain<String> first = iterator.next(); // a
		Assert.assertTrue(first.equals(stringChain));

		ValuesChain<String> second = iterator.next(); // b
		Assert.assertTrue(second.equals(stringChain.next()));

		ValuesChain<String> third = iterator.next(); // c
		ValuesChain<String> secondNext = second.next();
		Assert.assertTrue(third.equals(secondNext));

		iterator.remove(); // Remove c
		ValuesChain<String> fourth = iterator.next();
		ValuesChain<String> secondNewNext = second.next();
		Assert.assertTrue(fourth.equals(secondNewNext));

	}

	@Test
	public void testChainingString() {
		ValuesChain<String> stringChain = new ValuesChain<String>("a");
		stringChain.chain("b").chain("c").chain("d").chain("e");
		String temp = "";

		for (ValuesChain<String> string : stringChain) {
			temp += string.getValue();
		}

		Assert.assertTrue(temp.equals("abcde"));
	}

	@Test
	public void testSingleIteration() {
		ValuesChain<String> stringChain = new ValuesChain<String>("a");
		String temp = "";

		for (ValuesChain<String> string : stringChain) {
			temp += string.getValue();
		}

		Assert.assertTrue(temp.equals("a"));
	}

}
