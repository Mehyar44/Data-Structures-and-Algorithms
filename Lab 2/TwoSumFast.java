import java.util.Arrays;

public class TwoSumFast {
	public static void printAll(int[] a) {
    int n = a.length;
		Arrays.sort(a);
		if (containsDuplicates(a)) throw new IllegalArgumentException("array contains duplicate integers");
		for (int i = 0; i < n; i++) {
			int j = Arrays.binarySearch(a, -a[i]);
			if (j > i) System.out.println(a[i] + " " + a[j]);
		}
	} 

	public static int count(int[] a) {
		int n = a.length;
		Arrays.sort(a);
		if (containsDuplicates(a)) throw new IllegalArgumentException("array contains duplicate integers");
		int count = 0;
		for (int i = 0; i < n; i++) {
			int j = Arrays.binarySearch(a, -a[i]);
			if (j > i) count++;
		}
		return count;
	} 

		private static boolean containsDuplicates(int[] a) {
		for (int i = 1; i < a.length; i++)
			if (a[i] == a[i-1]) return true;
			return false;
		}

		public static void main(String[] args) throws java.io.IOException {
			if (args.length < 1) {
				System.out.println("Usage: java TwoSumFast <filename>");
				return;
			}
			java.util.List<Integer> list = new java.util.ArrayList<>();
			java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(args[0]));
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (!line.isEmpty()) {
					list.add(Integer.parseInt(line));
				}
			}
			br.close();
			int[] a = list.stream().mapToInt(i -> i).toArray();
			System.out.println("Pairs that sum to zero:");
			printAll(a);
			System.out.println("Total pairs: " + count(a));
		}
}
