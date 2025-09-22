public class TwoSum {
  public static void printAll(int[] a) {
    int n = a.length;
    for (int i = 0; i < n; i++)
      for (int j = i+1; j < n; j++)
        if (a[i] + a[j] == 0)
          System.out.println(a[i] + " " + a[j]);
  }

  public static void main(String[] args) throws java.io.IOException {
    if (args.length < 1) {
      System.out.println("Usage: java TwoSum <filename>");
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
  
  public static int count(int[] a) {
    int n = a.length;
    int count = 0;
    for (int i = 0; i < n; i++)
      for (int j = i+1; j < n; j++)
        if (a[i] + a[j] == 0)
          count++;
    return count;
  } 
}
