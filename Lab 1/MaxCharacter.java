import java.util.function.Function;

public class MaxCharacter {
	public static void main(String[] args) {
		Character[] charArray = {'H','E','L','L','O'};

		Function<Character[], Character> findMax = array -> {
			Character max = array[0];
			for (Character element : array)
				if (element.compareTo(max) > 0)
					max = element;
				return max;
		};

		System.out.println("The max character is: " + findMax.apply(charArray));
	}
}
