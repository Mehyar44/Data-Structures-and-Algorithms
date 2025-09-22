import java.util.Arrays;

public class ThreeSumFast {
  
  private ThreeSumFast() { }
  
  private static boolean containsDuplicates(int[] a) {
    for (int i = 1; i < a.length; i++)
      if (a[i] == a[i-1]) return true;
    return false;
  }

  public static void printAll(int[] a) {
    int n = a.length;
    Arrays.sort(a);
    if (containsDuplicates(a)) throw new IllegalArgumentException("array contains duplicate integers");
    for (int i = 0; i < n; i++)
      for (int j = i+1; j < n; j++) {
        int k = Arrays.binarySearch(a, -(a[i] + a[j]));
        if (k > j) System.out.println(a[i] + " " + a[j] + " " + a[k]);
      }    
  } 

  public static int count(int[] a) {
    int n = a.length;
    Arrays.sort(a);
    if (containsDuplicates(a)) throw new IllegalArgumentException("array contains duplicate integers");
    int count = 0;
    for (int i = 0; i < n; i++) 
      for (int j = i+1; j < n; j++) {
        int k = Arrays.binarySearch(a, -(a[i] + a[j]));
        if (k > j) count++;
      }
        
    return count;
  } 
  public static void main(String[] args) throws java.io.IOException {
    if (args.length < 1) {
      System.out.println("Usage: java ThreeSumFast <filename>");
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
    System.out.println("Triplets that sum to zero:");
    printAll(a);
    System.out.println("Total triplets: " + count(a));
  }
}
