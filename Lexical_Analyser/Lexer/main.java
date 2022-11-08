package Lexer;

import java.util.*;
import java.util.Scanner;
import java.io.*;

public class main {

	// Driver program to use lexer class
	public static void main(String[] args) {

		// HashSet<Character> seperators = new HashSet<Character>();
		// seperators.add('(');
		// seperators.add(')');
		// seperators.add(';');
		//
		// HashSet<Character> operators = new HashSet<Character>();
		// operators.add('=');
		// operators.add('<');
		//
		// HashSet<String> keywords = new HashSet<String>();
		// keywords.add("while");
		// keywords.add("for");

		// TEST PRINTRECORD FUNCTION
		ArrayList<String[]> record = new ArrayList<String[]>();
		String[] line1 = new String[] { "keyword", "while" };
		String[] line2 = new String[] { "separator", "(" };
		String[] line3 = new String[] { "identifier", "s" };
		String[] line4 = new String[] { "operator", "<" };
		String[] line5 = new String[] { "identifier", "upper" };
		String[] line6 = new String[] { "separator", ")" };
		String[] line7 = new String[] { "identifier", "t" };
		String[] line8 = new String[] { "operator", "=" };
		String[] line9 = new String[] { "real", "33.00" };
		String[] line10 = new String[] { "separator", ";" };

		record.add(line1);
		record.add(line2);
		record.add(line3);
		record.add(line4);
		record.add(line5);
		record.add(line6);
		record.add(line7);
		record.add(line8);
		record.add(line9);
		record.add(line10);

		printRecord(record);

		// TEST SPLITSTRING FUNCTION
		// String input_spaces = "while (s < upper) t = 33.00;";
		// String input_nospaces = "while(s<upper)t=33.00;";
		// ArrayList<String> output = splitString(input_spaces, seperators, operators);
		//
		// for (String word : output) {
		// System.out.println(word);
		// }

		// Create file and scanner instances
		// File file = new File("input_scode.txt");
		// Scanner scanFile = null;
		//
		// // Try to read file
		// try {
		// scanFile = new Scanner(file);
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}


	public static void printRecord(ArrayList<String[]> record) {
		File output_file = null;
		FileWriter writer = null;

		try {
			output_file = new File("output_file.txt");

			// If file already exists, delete it.
			if (!output_file.createNewFile())
				output_file.delete();

			writer = new FileWriter("output_file.txt");

			// Ouput headline to file
			writer.write(String.format("%-20s %s", "Token", "Lexeme\n"));
			writer.write("__________________________________\n");

			// Ouput each record to file
			for (String[] arr : record) {
				String padding = "                    ";
				padding = padding.substring(0, padding.length() - arr[0].length());
				writer.write(String.format("%s%s%-20s\n", arr[0], padding, arr[1]));
			}
			writer.close();

		} catch (IOException e) {
			System.out.println("Creating new file or writer failed");
			e.printStackTrace();
		}
		System.out.println("Done");
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
