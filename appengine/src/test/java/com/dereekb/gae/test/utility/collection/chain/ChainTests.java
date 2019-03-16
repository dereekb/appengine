package com.dereekb.gae.test.utility.collection.chain;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.utilities.collections.chain.impl.ValuesChain;

public class ChainTests {

	@Test
	public void testChain() {
		ValuesChain<String> stringChain = new ValuesChain<String>("a");
		stringChain.chain("b").chain("c").chain("d").chain("e");

		String string = stringChain.toString(",");
		assertTrue(string.equals("a,b,c,d,e"));
	}

	@Test
	public void testChainIterator() {
		ValuesChain<String> stringChain = new ValuesChain<String>("a");
		stringChain.chain("b").chain("c").chain("d").chain("e");

		Iterator<ValuesChain<String>> iterator = stringChain.chainIterable();
		ValuesChain<String> first = iterator.next(); // a
		assertTrue(first.equals(stringChain));

		ValuesChain<String> second = iterator.next(); // b
		assertTrue(second.equals(stringChain.next()));

		ValuesChain<String> third = iterator.next(); // c
		ValuesChain<String> secondNext = second.next();
		assertTrue(third.equals(secondNext));

		iterator.remove(); // Remove c
		ValuesChain<String> fourth = iterator.next();
		ValuesChain<String> secondNewNext = second.next();
		assertTrue(fourth.equals(secondNewNext));

	}

	@Test
	public void testChainingString() {
		ValuesChain<String> stringChain = new ValuesChain<String>("a");
		stringChain.chain("b").chain("c").chain("d").chain("e");
		String temp = "";

		for (ValuesChain<String> string : stringChain.chainIterable()) {
			temp += string.getValue();
		}

		assertTrue(temp.equals("abcde"));
	}

	@Test
	public void testSingleIteration() {
		ValuesChain<String> stringChain = new ValuesChain<String>("a");
		String temp = "";

		for (ValuesChain<String> string : stringChain.chainIterable()) {
			temp += string.getValue();
		}

		assertTrue(temp.equals("a"));
	}

}
