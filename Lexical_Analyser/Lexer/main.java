package Lexer;

import java.util.*;
import java.util.Scanner;
import java.io.*;

public class main {

	// Driver program to use lexer class
	public static void main(String[] args) {

		HashSet<Character> seperators = new HashSet<Character>();
		seperators.add('(');
		seperators.add(')');
		seperators.add(';');

		HashSet<Character> operators = new HashSet<Character>();
		operators.add('=');
		operators.add('<');

		HashSet<String> keywords = new HashSet<String>();
		keywords.add("while");
		keywords.add("for");

		// Test splitString function
		String input_spaces = "while  (s < upper)   t = 33.00;";
		String input_nospaces = "while(s<upper)t=33.00;";
		ArrayList<String> output = splitString(input_spaces, seperators, operators);

		for (String word : output) {
			System.out.println(word);
		}

		// Create file and scanner instances
		File file = new File("input_scode.txt");
		Scanner scanFile = null;

		// Try to read file
		try {
			scanFile = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static ArrayList<String[]> lexer(ArrayList<String> substrings, HashSet<Character> seperators,
			HashSet<Character> operators, HashSet<String> keywords) {

		return null;
	}


	public static ArrayList<String> splitString(String str, HashSet<Character> seperators,
			HashSet<Character> operators) {
		ArrayList<String> substrings = new ArrayList<String>();
		int wordCount = 0;

		for (int i = 0; i < str.length(); i += wordCount) {
			StringBuilder sb = new StringBuilder();
			for (int j = i; j < str.length(); j++) {
				char curr = str.charAt(j);
				wordCount = j - i;

				// if (wordCount == 0)
				// wordCount = 1;

				// Space has been reached
				if (curr == ' ') {
					if (sb.toString().compareTo("") == 0) {
						wordCount++;
						break;
					}
					substrings.add(sb.toString());
					break;
				}

				// Character is a seperator or operator
				if (seperators.contains(curr) || operators.contains(curr)) {
					if (sb.toString().length() > 0)
						substrings.add(sb.toString());
					substrings.add(String.valueOf(curr));
					wordCount++;
					break;
				} else {
					sb.append(curr);
				}
				if (wordCount == 0)
					wordCount = 1;
			}
		}
		return substrings;
	}
}
