package lambdasinaction.chap13;

import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @See <a href="https://dzone.com/articles/java-8-master-permutations">Java 8: Master Permutations from DZone</a>
 */
public final class Permutations {
	private Permutations() {
	}

	public static long factorial(int n) {
		if (n > 20 || n < 0) throw new IllegalArgumentException(n + "is out of range!");
		return LongStream.rangeClosed(2, n).reduce(1, (a, b) -> a * b);
	}

	private static <T> List<T> permutation(long no, List<T> items) {
		return permutation(no, new LinkedList<>(Objects.requireNonNull(items)), new ArrayList<>());
	}

	private static <T> List<T> permutation(long no, LinkedList<T> in, List<T> out) {
		if (in.isEmpty()) return out;

		long subFactorial = factorial(in.size() - 1);
		out.add(in.remove((int) (no / subFactorial)));
		return permutation((int) (no % subFactorial), in, out);
	}

	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <T> Stream<Stream<T>> of(T... items) {
		List<T> itemList = Arrays.asList(items);

		return LongStream.range(0, factorial(items.length)).mapToObj(no -> permutation(no, itemList).stream());
	}
}
