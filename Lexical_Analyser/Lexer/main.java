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

		// Create file and scanner instances
		File file = new File("input_scode.txt");
		Scanner scanFile = null;

		// Try to read file
		try {
			scanFile = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ArrayList<String> lines = new ArrayList<String>();
		while (scanFile.hasNextLine()) {
			String line = scanFile.nextLine();
			lines.add(line);
		}

		ArrayList<String> substrings = new ArrayList<String>();
		// Run each line through the splitString funciton
		for (String line : lines) {
			substrings.addAll(splitString(line, seperators, operators));
		}

		ArrayList<String[]> records = lexer(substrings, seperators, operators, keywords);
		printRecord(records);

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
		// Iterate through each substring in substrings
		ArrayList<String[]> records = new ArrayList<String[]>();

		for (String substring : substrings) {
			String[] record = new String[2];
			String token = null;

			// Check length and if it is a seperator or operator
			if (substring.length() > 1) {
				if (keywords.contains(substring)) // KEYWORD
					token = "keyword";
				else if (isNumeric(substring)) // REAL
					token = "real";
				else // IDENTIFIER
					token = "identifier";
			} else if (substring.length() == 1) {
				if (seperators.contains(substring.charAt(0))) // SEPERATOR
					token = "seperator";
				else if (operators.contains(substring.charAt(0))) // OPERATOR
					token = "operator";
				else // IDENTIFIER
					token = "identifier";
			}
			// Add values to record
			record[0] = token;
			record[1] = substring;

			// Add record to records
			records.add(record);
		}
		return records;
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


	public static boolean isNumeric(String str) {
		try {
			Float.parseFloat(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
