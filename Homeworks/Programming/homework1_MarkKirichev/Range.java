/**
 * A Range iterable that can be used to iterate over a sequence of integers
 * (similar to Python's range function).
 */

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class Range implements Iterable<Integer> {
	// you probably need some variables here and maybe an inner class.
	private final int min;
	private final int max;
	private final int increment;

	public Range(int min, int max, int increment) {
		// change this

		// checking whether the range is valid
		if((min < max && increment <= 0) || (min > max && increment >= 0)) {
			throw new UnsupportedOperationException("This is an invalid range definition!");
		}

		this.min = min;
		this.max = max;
		this.increment = increment;
	}

	public Range(int min, int max) {
		// change this

		// overloaded function in case increment value is not provided
		this(min, max, 1);
	}

	public java.util.Iterator<Integer> iterator() {
		// change this
		return new IteratorRange();
	}

	private class IteratorRange implements Iterator<Integer> {
		private final int iterMax;
		private final int iterIncrement;
		private int current;

		public IteratorRange() {
			this.current = min; // starting value for the iteration
			this.iterMax = max; // value at which the iteration should be terminated
			this.iterIncrement = increment;
		}

		@Override
		public boolean hasNext() {
			if(this.iterIncrement > 0) {
				return this.current < this.iterMax;
			} else {
				return this.current > this.iterMax;
			}
		}

		@Override
		public Integer next() {
			Integer nextVal = this.current;
			this.current += this.iterIncrement;
			return nextVal;
		}
	}

	static void printRange(Range range) {
		for (Integer j : range) {
			System.out.print(j + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {

		ArrayList<Range> rangesTestArray = new ArrayList<Range>(
				List.of(new Range(1, 8, 1),  // Prints the sequence 1 2 3 4 5 6 7
						new Range(1, 8, 2),  // Prints the sequence 1 3 5 7
						new Range(1, 8),     // Prints the sequence 1 2 3 4 5 6 7
						new Range(8, 0, -1)) // Prints the sequence 8 7 6 5 4 3 2 1
		);

		rangesTestArray.forEach(Range::printRange);
	}
}