package Lexer;

import java.util.*;
import java.util.Scanner;
import java.io.*;
import java.io.FileWriter;

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
		ArrayList<String> tokens = lexer(output, seperators, operators, keywords);
		//System.out.printf("%-15s %-15s \n", "token", "lexeme");

		try {
			FileWriter outputFile = new FileWriter("outputfile.txt");
			// establishes formatted writing to file
			PrintWriter printToOutput = new PrintWriter(outputFile);
			// Setting up top two rows before token and lexemes outputs
			printToOutput.printf("%-15s %-15s \n", "token", "lexeme");
			printToOutput.println("__________________________");

			for (String word : output) {
				printToOutput.printf("%-15s %-15s \n", tokens, word);
			}
			
			printToOutput.close();
			System.out.println("Wrote to file outputfile.txt");
		}

		catch (IOException e) {
			System.out.println("An Error Occured.");
			e.printStackTrace();
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
	}// end main

	public static ArrayList<String> lexer(ArrayList<String> substrings, HashSet<Character> seperators,
			HashSet<Character> operators, HashSet<String> keywords) {
		ArrayList<String> token = new ArrayList<String>();

		for (String subs : substrings) {
			if (keywords.contains(subs)) {
				token.add("keyword");
				break;
			}

			else if (seperators.contains(subs)) {
				token.add("seperator");
				break;
			}

			else if (operators.contains(subs)) {
				token.add("operator");
				break;
			}

			else {
				token.add("identifier");
			}
		}

		return token;

	}// end ArrayList method

	public static ArrayList<String> splitString(String str, HashSet<Character> seperators,
			HashSet<Character> operators) {
		ArrayList<String> substrings = new ArrayList<String>();
		int wordCount = 0;

		for (int i = 0; i < str.length(); i += wordCount) {
			StringBuilder sb = new StringBuilder();
			for (int j = i; j < str.length(); j++) {
				char curr = str.charAt(j);
				wordCount = j - i;

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
		} // end long for loop
		return substrings;
	}// end substring method
	
}// end class
