import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.*;

public class Project {

	private static List<String> functions = new ArrayList<String>();
	private static List<String> arithmeticOperators = new ArrayList<String>();
	private static List<String> result = new ArrayList<String>();

	private enum Operator {
		ADD(1), SUBTRACT(2), MULTIPLY(3), DIVIDE(4);
		final int precedence;

		Operator(int p) {
			precedence = p;
		}
	}

	private static Map<String, Operator> ops = new HashMap<String, Operator>() {
		{
			put("+", Operator.ADD);
			put("-", Operator.SUBTRACT);
			put("*", Operator.MULTIPLY);
			put("/", Operator.DIVIDE);
		}
	};

	private static boolean isHigherPrecedence(String op, String sub) {
		return (ops.containsKey(sub) && ops.get(sub).precedence >= ops.get(op).precedence);
	}

	public static void filler() {
		String[] operators = { "+", "-", "*", "/" };
		for (String s : operators) {
			arithmeticOperators.add(s);
		}

		functions.add("log");
		functions.add("pow");
		functions.add("sqrt");
		functions.add("sin");
		functions.add("cos");
		functions.add("tan");
		functions.add("cotan");
	}

	public static List<String> getTokens(String input) {

		StringBuilder word = new StringBuilder();
		StringBuilder number = new StringBuilder();

		for (int i = 0; i < input.length(); i++) {

			if (Character.isAlphabetic(input.charAt(i))) {
				word.append(input.charAt(i));
			} else if (word.length() != 0) {
				result.add(word.toString());
				word.setLength(0);
			}

			if (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.') {
				number.append(input.charAt(i));
			} else if (number.length() > 0) {
				result.add(number.toString());
				number.setLength(0);
			}

			if (input.charAt(i) == '-'
					&& (i == 0 || input.charAt(i - 1) == ',' || input
							.charAt(i - 1) == '(')) {
				number.append('-');
			} else if (input.charAt(i) == '+' || input.charAt(i) == '-'
					|| input.charAt(i) == '(' || input.charAt(i) == ')'
					|| input.charAt(i) == '*' || input.charAt(i) == '/'
					|| input.charAt(i) == ',') {
				result.add(Character.toString(input.charAt(i)));
			}
		}

		if (number.length() != 0) {
			result.add(number.toString());
		}
		if (word.length() != 0) {
			result.add(word.toString());
		}

		return result;
	}

	public static Queue<String> fromInfixToReversePolishNotation(
			List<String> tokens) {

		Stack<String> operatorStack = new Stack<String>();
		Queue<String> operandQueue = new LinkedList<String>();

		for (int i = 0; i < tokens.size(); i++) {
			String currentToken = tokens.get(i);

			if (currentToken.matches("^-?[0-9]+(\\.[0-9]+)?$")
					|| currentToken.matches("pi") || currentToken.matches("e")) {
				operandQueue.add(currentToken);
			}

			else if (functions.contains(currentToken)) {
				operatorStack.push(currentToken);
			}

			else if (currentToken.equals(",")) {

				while (operatorStack.peek().equals("(")) {
					operandQueue.add(operatorStack.pop());
				}
			}

			else if (arithmeticOperators.contains(currentToken)) {
				while (!operatorStack.isEmpty()
						&& isHigherPrecedence(currentToken,
								operatorStack.peek())) {
					operandQueue.add(operatorStack.pop());
				}
				operatorStack.push(currentToken);
			}

			else if (currentToken.equals("(")) {
				operatorStack.push("(");
			}

			else if (currentToken.equals(")")) {

				while (!operatorStack.isEmpty()
						&& !(operatorStack.peek().equals("("))) {
					operandQueue.add(operatorStack.pop());
				}

				if (!operatorStack.isEmpty()) {
					operatorStack.pop();
				}

				if (!operatorStack.isEmpty()
						&& functions.contains(operatorStack.peek())) {
					operandQueue.add(operatorStack.pop());
				}
			}

		}
		while (!operatorStack.isEmpty()) {
			if (operatorStack.peek().equals("(")
					|| operatorStack.peek().equals(")")) {
				throw new IllegalArgumentException("Invalid parentheses");
			}

			operandQueue.add(operatorStack.pop());
		}
		return operandQueue;
	}

	public static double reversePolishNotation(Queue<String> input) {

		Stack<Double> valueStack = new Stack<Double>();

		while (!input.isEmpty()) {
			String currentToken = input.remove();
			if (currentToken.matches("^-?[0-9]+(\\.[0-9]+)?$")) {
				valueStack.push(Double.valueOf(currentToken));
			} else if (currentToken.equals("pi")) {
				valueStack.push(Math.PI);
			} else if (currentToken.equals("e")) {
				valueStack.push(Math.E);
			} else if (arithmeticOperators.contains(currentToken)
					|| functions.contains(currentToken)) {
				if (currentToken.equals("+")) {
					if (valueStack.size() < 2) {
						throw new IllegalArgumentException("Invalid expression");
					}
					double firstValue = valueStack.pop();
					double secondValue = valueStack.pop();

					valueStack.push(firstValue + secondValue);

				} else if (currentToken.equals("-")) {
					if (valueStack.size() < 2) {
						throw new IllegalArgumentException("Invalid expression");
					}

					double firstValue = valueStack.pop();
					double secondValue = valueStack.pop();

					valueStack.push(secondValue - firstValue);
				} else if (currentToken.equals("*")) {
					if (valueStack.size() < 2) {
						throw new IllegalArgumentException("Invalid expression");
					}

					double firstValue = valueStack.pop();
					double secondValue = valueStack.pop();

					valueStack.push(firstValue * secondValue);
				} else if (currentToken.equals("/")) {
					if (valueStack.size() < 2) {
						throw new IllegalArgumentException("Invalid expression");
					}

					double firstValue = valueStack.pop();
					double secondValue = valueStack.pop();

					valueStack.push(secondValue / firstValue);
				} else if (currentToken.equals("pow")) {
					if (valueStack.size() < 2) {
						throw new IllegalArgumentException("Invalid expression");
					}

					double firstValue = valueStack.pop();
					double secondValue = valueStack.pop();

					valueStack.push(Math.pow(secondValue, firstValue));
				} else if (currentToken.equals("sqrt")) {
					if (valueStack.size() < 1) {
						throw new IllegalArgumentException("Invalid expression");
					}

					double value = valueStack.pop();

					valueStack.push(Math.sqrt(value));
				} else if (currentToken.equals("log")) {
					if (valueStack.size() < 1) {
						throw new IllegalArgumentException("Invalid expression");
					}

					double value = valueStack.pop();

					valueStack.push(Math.log(value));
				} else if (currentToken.equals("sin")) {
					if (valueStack.size() < 1) {
						throw new IllegalArgumentException("Invalid expression");
					}

					double value = valueStack.pop();

					valueStack.push(Math.sin(value));
				} else if (currentToken.equals("cos")) {
					if (valueStack.size() < 1) {
						throw new IllegalArgumentException("Invalid expression");
					}

					double value = valueStack.pop();

					valueStack.push(Math.cos(value));

				} else if (currentToken.equals("tan")) {
					if (valueStack.size() < 1) {
						throw new IllegalArgumentException("Invalid expression");
					}

					double value = valueStack.pop();

					valueStack.push(Math.tan(value));
				} else if (currentToken.equals("cotan")) {
					if (valueStack.size() < 1) {
						throw new IllegalArgumentException("Invalid expression");
					}

					double value = valueStack.pop();

					valueStack.push(Math.tan(1 / value));
				}
			}
		}

		if (valueStack.size() == 1) {
			return valueStack.pop();
		} else {
			throw new IllegalArgumentException();
		}
	}

}