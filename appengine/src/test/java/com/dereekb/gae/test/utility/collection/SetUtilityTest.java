package com.dereekb.gae.test.utility.collection;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.utilities.collections.list.SetUtility.SetDifferenceImpl;

public class SetUtilityTest {

	@Test()
	public void testSetUtilityHasAny() {
		
		Set<Integer> setA = new HashSet<Integer>();

		setA.add(1);
		setA.add(2); // Included in both
		
		Set<Integer> setB = new HashSet<Integer>();

		setB.add(2); // Included in both
		setB.add(3); // Included in both
	
		assertTrue(SetUtility.containsAnyElementsOf(setA, setB));
		
		setB.remove(2);
		
		assertFalse(SetUtility.containsAnyElementsOf(setA, setB));
	}

	@Test()
	public void testSetUtilityIntersection() {
		
		Set<Integer> setA = new HashSet<Integer>();

		setA.add(1);
		setA.add(2); // Included in both
		setA.add(3); // Included in both
		
		Set<Integer> setB = new HashSet<Integer>();

		setB.add(2); // Included in both
		setB.add(3); // Included in both
		setB.add(4);
		
		SetDifferenceImpl<Integer> difference = SetUtility.makeSetInfo(setA, setB);
		
		Set<Integer> intersection = difference.getIntersection();
		
		assertFalse(intersection.contains(1));
		assertTrue(intersection.contains(2));
		assertTrue(intersection.contains(3));
		assertFalse(intersection.contains(4));
	}

	@Test()
	public void testSetUtilityCompliment() {
		
		Set<Integer> setA = new HashSet<Integer>();

		setA.add(1); 
		setA.add(2);
		setA.add(3);
		
		Set<Integer> setB = new HashSet<Integer>();

		setB.add(2);
		setB.add(3);
		setB.add(4); // Should be the only one contained.
		
		SetDifferenceImpl<Integer> difference = SetUtility.makeSetInfo(setA, setB);
		
		Set<Integer> compliment = difference.getCompliment();
		
		assertFalse(compliment.contains(1));
		assertFalse(compliment.contains(2));
		assertFalse(compliment.contains(3));
		assertTrue(compliment.contains(4));
	}

	@Test()
	public void testSetUtilityDifference() {
		
		Set<Integer> setA = new HashSet<Integer>();

		setA.add(1); // Unique to setA	
		setA.add(2);
		setA.add(3);
		
		Set<Integer> setB = new HashSet<Integer>();

		setB.add(2);
		setB.add(3);
		setB.add(4); // Unique to setB
		
		SetDifferenceImpl<Integer> difference = SetUtility.makeSetInfo(setA, setB);
		
		Set<Integer> setDifference = difference.getDifference();
		
		assertTrue(setDifference.contains(1));
		assertFalse(setDifference.contains(2));
		assertFalse(setDifference.contains(3));
		assertTrue(setDifference.contains(4));
	}

	@Test()
	public void testSetUtilityUnique() {
		
		Set<Integer> setA = new HashSet<Integer>();

		setA.add(1); // Unique to setA	
		setA.add(2);
		setA.add(3);
		
		Set<Integer> setB = new HashSet<Integer>();

		setB.add(2);
		setB.add(3);
		setB.add(4); // Unique to setB
		
		SetDifferenceImpl<Integer> difference = SetUtility.makeSetInfo(setA, setB);
		
		Set<Integer> setDifference = difference.getUnique();
		
		assertTrue(setDifference.contains(1));
		assertFalse(setDifference.contains(2));
		assertFalse(setDifference.contains(3));
		assertFalse(setDifference.contains(4));
	}

	@Test()
	public void testIsEquivalent() {

		Set<Integer> setA = new HashSet<Integer>();

		setA.add(1);
		setA.add(2);
		setA.add(3);

		Set<Integer> setB = new HashSet<Integer>();

		setB.add(2);
		setB.add(3);
		setB.add(4);

		Set<Integer> setC = new HashSet<Integer>(setA);

		assertFalse(SetUtility.isEquivalent(setA, setB));
		assertTrue(SetUtility.isEquivalent(setA, setA));
		
		assertTrue(SetUtility.isEquivalent(setA, setC));
		assertTrue(SetUtility.isEquivalent(setC, setA));
		
		setC.add(4);
		
		assertFalse(SetUtility.isEquivalent(setA, setC));
		assertFalse(SetUtility.isEquivalent(setC, setA));
		
	}

}
