package test;

import java.util.Iterator;
import java.util.stream.IntStream;

/**
 *
 */
public class PrimesStreamGenerator {

	public static IntStream integers() {
		return IntStream.iterate(2, n -> n + 1);
	}

	public static IntStream primes(IntStream numbers) {
		Iterator<Integer> i = numbers.iterator();
		int head = i.next();
		Lazy<Iterator<Integer>> lazyTail = new Lazy<>() {
			public Iterator<Integer> init() {
				return primes(IntStream.generate(() -> i.next()))
						.filter(t -> t % head != 0)
						.iterator();
			}
		};

		return IntStream.iterate(
				head,
				n -> lazyTail.get().next());
	}

	public static abstract class Lazy<T> {
		private T value;

		public abstract T init();

		public final T get() {
			return value == null ? value = init() : value;
		}
	}

	/**
	 * 
	 */
	public static void main(String[] args) {
		IntStream primes = primes(integers()).limit(20);
		primes.forEach(i -> System.out.println(i));
	}
}
