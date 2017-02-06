package com.dereekb.gae.utilities.collections.list;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SetUtility {

	@SafeVarargs
	public static <T> Set<T> makeSet(T... objects) {
		Set<T> set = new HashSet<T>();

		for (T object : objects) {
			set.add(object);
		}

		return set;
	}

	public static <T> SetDifferenceImpl<T> getDifference(Collection<? extends T> a,
	                                                     Collection<? extends T> b) {
		return new SetDifferenceImpl<T>(a, b);
	}

	public static <T> Set<T> combine(Collection<? extends T> a,
	                                 Collection<? extends T> b) {
		Set<T> set = new HashSet<T>(a);
		set.addAll(b);
		return set;
	}

	public static class SetDifferenceImpl<T>
	        implements SetDifference<T> {

		private final Set<T> setA;
		private final Set<T> setB;

		private Set<T> intersection;
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
		public Set<T> getCompliment() {
			if (this.compliment == null) {
				this.compliment = makeCompliment(this.setA, this.setB);
			}

			return this.compliment;
		}

		@Override
		public Set<T> getDifference() {
			if (this.difference == null) {
				this.difference = makeDifference(this.setA, this.setB);
			}

			return this.difference;
		}

		public static <T> Set<T> makeIntersection(Set<T> a,
		                                          Set<T> b) {
			Set<T> intersection = new HashSet<T>(a);
			intersection.removeAll(b);
			return intersection;
		}

		public static <T> Set<T> makeCompliment(Set<T> a,
		                                        Set<T> b) {
			Set<T> compliment = new HashSet<T>(a);
			compliment.retainAll(b);
			return compliment;
		}

		public static <T> Set<T> makeDifference(Set<T> a,
		                                        Set<T> b) {
			Set<T> aCompliment = makeCompliment(a, b);
			Set<T> bCompliment = makeCompliment(b, a);

			return SetUtility.combine(aCompliment, bCompliment);
		}
	}

	public static interface SetDifference<T> {

		public Collection<T> getSetA();

		public Collection<T> getSetB();

		/**
		 * Gets all elements that are in both sets.
		 */
		public Set<T> getIntersection();

		/**
		 * Gets all elements that are not in set A.
		 */
		public Set<T> getCompliment();

		/**
		 * Get all elements that are unique to either set.
		 */
		public Set<T> getDifference();

	}

}
