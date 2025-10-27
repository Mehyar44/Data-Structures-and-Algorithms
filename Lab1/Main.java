public class Main {
	public static void main(String[] args) {
		Integer[] intArray = {1, 2, 3, 4, 5};
		Double[] doubArray = {1.1, 2.2, 3.3, 4.4};
		Character[] charArray = {'H','E','L','L','O'};
		String[] strArray = {"once", "upon", "a", "time"};
		
		printArray(intArray);
		printArray(doubArray);
		printArray(charArray);
		printArray(strArray);

		System.out.println("Max Integer is: " + getMax(intArray));
		System.out.println("Max Double is: " + getMax(doubArray));
		System.out.println("Max Character is: " + getMax(charArray));
		System.out.println("Max String is: " + getMax(strArray));
	}
	
	/*
	public static void printArray(Object[] array) {
		for (Object element : array) 
			System.out.print(element + ", ");
		System.out.println();
	}

	public static void printArray(Integer[] array) {
		for (Integer element : array) 
			System.out.print(element + ", ");
		System.out.println();
	}

	public static void printArray(Double[] array) {
		for (Double element : array) 
			System.out.print(element + ", ");
		System.out.println();
	}

	public static void printArray(Character[] array) {
		for (Character element : array) 
			System.out.print(element + ", ");
		System.out.println();
	}

	public static void printArray(String[] array) {
		for (String element : array) 
			System.out.print(element + ", ");
		System.out.println();
	}

	public static Comparable getMax(Comparable[] anArray) {
		Comparable max = anArray[0];
		for (Comparable element : anArray)
			if (element.compareTo(max) > 0)
				max = element;
		return max;
	}
	*/

	public static <E> void printArray(E[] array) {
		for (E element : array) 
			System.out.print(element + ", ");
		System.out.println();
	}

	public static <E extends Comparable<? super E>> E getMax(E[] array) {
		E max = array[0];
		for (E element : array)
			if (element.compareTo(max) > 0)
				max = element;
		return max;
	}
}
