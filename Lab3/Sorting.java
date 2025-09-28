import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Arrays;
import java.io.File;
import java.util.Scanner;

public class Sorting {

    public static void main(String[] args)  { 
        if (args.length < 2) {
            System.err.println("Usage: java Sorting input.txt algorithmIndex");
            return;
        }
        int[] a = null;
        try {
            Scanner scanner = new Scanner(new File(args[0]));
            java.util.ArrayList<Integer> list = new java.util.ArrayList<>();
            while (scanner.hasNextInt()) {
                list.add(scanner.nextInt());
            }
            a = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                a[i] = list.get(i);
            }
            scanner.close();
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }
        int[] b = a.clone();
        Arrays.sort(b); // sorted array
        int[] c = new int[b.length];
        for (int i = 0; i < b.length; i++) {
            c[i] = b[b.length - 1 - i]; // reversed array
        }
        int[] d = b.clone();
        int swaps = (int)(0.1 * d.length);
        java.util.Random rand = new java.util.Random();
        for (int i = 0; i < swaps; i++) {
            int idx1 = rand.nextInt(d.length);
            int idx2 = rand.nextInt(d.length);
            int temp = d[idx1];
            d[idx1] = d[idx2];
            d[idx2] = temp; // almost sorted array
        }

        int algo = Integer.parseInt(args[1]);
        String[] algoNames = {"Arrays.sort", "Bubble Sort", "Selection Sort", "Quicksort", "Mergesort", "Insertion Sort"};
        String inputFile = args[0];
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

        int[][] arrays = {a, b, c, d};
        String[] arrayNames = {"a", "b", "c", "d"};
        String[] outFiles = {"a.txt", "b.txt", "c.txt", "d.txt"};

        for (int i = 0; i < arrays.length; i++) {
            int[] arr = arrays[i].clone();
            long start = System.currentTimeMillis();
            switch (algo) {
                case 0:
                    java.util.Arrays.sort(arr);
                    break;
                case 1:
                    bubbleSort(arr);
                    break;
                case 2:
                    selectionSort(arr);
                    break;
                case 3:
                    quickSort(arr, 0, arr.length - 1);
                    break;
                case 4:
                    mergeSort(arr, 0, arr.length - 1);
                    break;
                case 5:
                    insertionSort(arr);
                    break;
                default:
                    System.err.println("Invalid algorithm index. Use 0-5.");
                    return;
            }
            double time = System.currentTimeMillis() - start;
            System.out.printf("%s %s %8.1f   %s  %s  %s\n", algoNames[algo], arrayNames[i], time, timeStamp, inputFile);
            writeArrayToFile(arr, outFiles[i]);
        }
    }

    // Mergesort
    private static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }
    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        int[] L = new int[n1];
        int[] R = new int[n2];
        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) arr[k++] = L[i++];
            else arr[k++] = R[j++];
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    // Insertion Sort
    private static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // Bubble Sort
    private static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // Selection Sort
    private static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }

    // Quicksort
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    private static void writeArrayToFile(int[] arr, String filename) {
        try (java.io.PrintWriter out = new java.io.PrintWriter(filename)) {
            for (int num : arr) {
                out.println(num);
            }
        } catch (Exception e) {
            System.err.println("Error writing to " + filename + ": " + e.getMessage());
        }
    }
}