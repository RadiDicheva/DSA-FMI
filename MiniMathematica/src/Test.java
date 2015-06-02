import java.util.List;
import java.util.Scanner;

public class Test extends Project {
	public static void main(String[] args) {

		StringBuilder sb = new StringBuilder();
		Scanner scan = new Scanner(System.in);
		System.out.printf("User input: ");
		String inputString = scan.nextLine(); // 5 + sin(pi) / pow(2, 10) - log(e, pow(e, sqrt(4)));
		inputString = inputString.replaceAll("log\\(e,", "log(");
		inputString = inputString.replaceAll("\\s+", "");
		sb.append(inputString);

		filler();

		List<String> check = getTokens(inputString);
		System.out.println(check);
		System.out.println(fromInfixToReversePolishNotation(check));
		System.out
				.println(reversePolishNotation(fromInfixToReversePolishNotation(check)));

		scan.close();
	}
}
