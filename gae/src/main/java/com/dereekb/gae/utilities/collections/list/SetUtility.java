package com.dereekb.gae.utilities.collections.list;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SetUtility {

	public static <T> Set<T> wrap(T value) {
		Set<T> set = new HashSet<T>();

		if (value != null) {
			set.add(value);
		}

		return set;
	}

	@SafeVarargs
	public static <T> Set<T> makeSet(T... objects) {
		Set<T> set = new HashSet<T>();

		for (T object : objects) {
			set.add(object);
		}

		return set;
	}

	public static <T> SetDifferenceImpl<T> makeSetInfo(Collection<? extends T> a,
	                                                     Collection<? extends T> b) {
		return new SetDifferenceImpl<T>(a, b);
	}

	public static <T> Set<T> combine(Collection<? extends T> a,
	                                 Collection<? extends T> b) {
		Set<T> set = new HashSet<T>(a);
		set.addAll(b);
		return set;
	}

	public static <T> Set<T> copy(Set<T> input) {
		Set<T> set = new HashSet<T>();
		
		if (input != null) {
			set.addAll(input);
		}
		
		return set;
	}

	public static <T> boolean isEquivalent(Set<T> a,
	                                   Set<T> b) {
		return a.size() == b.size() && a.containsAll(b);
	}

	public static class SetDifferenceImpl<T>
	        implements SetInfo<T> {

		private final Set<T> setA;
		private final Set<T> setB;

		private Set<T> intersection;
		private Set<T> unique;
		private Set<T> compliment;
		private Set<T> difference;

		public SetDifferenceImpl(Collection<? extends T> a, Collection<? extends T> b) {
			this.setA = new HashSet<T>(a);
			this.setB = new HashSet<T>(b);
		}

		@Override
		public Collection<T> getSetA() {
			return this.setA;
		}

		@Override
		public Collection<T> getSetB() {
			return this.setB;
		}

		@Override
		public Set<T> getIntersection() {
			if (this.intersection == null) {
				this.intersection = makeIntersection(this.setA, this.setB);
			}

			return this.intersection;
		}

		@Override
		public Set<T> getUnique() {
			if (this.unique == null) {
				this.unique = makeUnique(this.setA, this.setB);
			}

			return this.unique;
		}

		@Override
		public Set<T> getCompliment() {
			if (this.compliment == null) {
				this.compliment = makeCompliment(this.setA, this.setB);
			}

			return this.compliment;
		}

		@Override
		public Set<T> getDifference() {
			if (this.difference == null) {
				this.difference = this.makeDifference(this.setA, this.setB);
			}

			return this.difference;
		}

		public static <T> Set<T> makeIntersection(Set<T> a,
		                                          Set<T> b) {
			Set<T> intersection = new HashSet<T>(a);
			intersection.retainAll(b);
			return intersection;
		}

		public static <T> Set<T> makeUnique(Set<T> a, Set<T> b) {
			return makeCompliment(b, a);
		}

		public static <T> Set<T> makeCompliment(Set<T> a,
		                                        Set<T> b) {
			Set<T> compliment = new HashSet<T>(b);
			compliment.removeAll(a);
			return compliment;
		}

		public Set<T> makeDifference(Set<T> a, Set<T> b) {
			Set<T> aUnique = this.getUnique();
			Set<T> bCompliment = this.getCompliment();
			return SetUtility.combine(aUnique, bCompliment);
		}
		
	}

	public static interface SetInfo<T> {

		public Collection<T> getSetA();

		public Collection<T> getSetB();

		/**
		 * Gets all elements that are in both sets.
		 */
		public Set<T> getIntersection();
		
		/**
		 * Gets all elements that are unique to set A.
		 */
		public Set<T> getUnique();

		/**
		 * Gets all elements that are not in set A (unique to set B).
		 */
		public Set<T> getCompliment();

		/**
		 * Get all elements that are unique to either set.
		 */
		public Set<T> getDifference();

	}

}
