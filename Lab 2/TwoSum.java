public class TwoSum {
  public static void printAll(int[] a) {
    int n = a.length;
    for (int i = 0; i < n; i++)
      for (int j = i+1; j < n; j++)
        if (a[i] + a[j] == 0)
          StdOut.println(a[i] + " " + a[j]);
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
