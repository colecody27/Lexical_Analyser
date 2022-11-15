/*
 * Authors: Cody Cole, Aidan Castillo
 * CPSC 323  
 * 11/13/22
 */

package Lexer;

import java.util.*;
import java.util.Scanner;
import java.io.*;

public class main {

	// Driver program to use lexer class
	public static void main(String[] args) {
		// Create hashsets that will identify keys
		HashSet<Character> seperators = new HashSet<Character>();
		seperators.add('(');
		seperators.add(')');
		seperators.add('{');
		seperators.add('}');
		seperators.add('[');
		seperators.add(']');
		seperators.add(',');
		seperators.add(';');
		HashSet<String> operators = new HashSet<String>();
		operators.add("=");
		operators.add("<");
		operators.add(">");
		operators.add("*");
		operators.add("+");
		operators.add("-");
		operators.add("/");
		operators.add("%");
		operators.add("!=");
		operators.add("<=");
		operators.add(">=");
		operators.add("++");
		operators.add("--");
		operators.add("==");
		operators.add("&&");
		operators.add("||");
		operators.add("!");
		operators.add("&");
		operators.add("|");
		operators.add("<<");
		operators.add(">>");
		operators.add("~");
		operators.add("^");
		operators.add("+=");
		operators.add("-=");
		operators.add("*=");
		operators.add("/=");
		operators.add("%=");
		operators.add("?:");
		HashSet<String> keywords = new HashSet<String>();
		keywords.add("while");
		keywords.add("for");
		keywords.add("asm");
		keywords.add("auto");
		keywords.add("break");
		keywords.add("case");
		keywords.add("catch");
		keywords.add("char");
		keywords.add("class");
		keywords.add("const");
		keywords.add("continue");
		keywords.add("default");
		keywords.add("delete");
		keywords.add("do");
		keywords.add("double");
		keywords.add("else");
		keywords.add("enum");
		keywords.add("extern");
		keywords.add("float");
		keywords.add("friend");
		keywords.add("goto");
		keywords.add("if");
		keywords.add("inline");
		keywords.add("int");
		keywords.add("long");
		keywords.add("new");

		// Create file and scanner instances
		File file = new File("input_scode.txt");
		Scanner scanFile = null;

		// Setup scanner to read file
		try {
			scanFile = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Read in each line of file
		ArrayList<String> lines = new ArrayList<String>();
		while (scanFile.hasNextLine()) {
			String line = scanFile.nextLine();
			lines.add(line);
		}

		// Run each line through the splitString funciton. Returns an arraylist of values.
		ArrayList<String> values = new ArrayList<String>();
		for (String line : lines) {
			values.addAll(splitString(line, seperators, operators));
		}

		// Run values through lexer which will return an arraylist of string arrays containing [key, value].
		ArrayList<String[]> records = lexer(values, seperators, operators, keywords);
		printRecords(records);
	}


	// Receives list of string arrays and outputs to a file. Will delete file if already existing.
	public static void printRecords(ArrayList<String[]> records) {
		File output_file = null;
		FileWriter writer = null;

		try {
			output_file = new File("output.txt");

			// If file already exists, delete it.
			if (!output_file.createNewFile())
				output_file.delete();

			writer = new FileWriter("output.txt");

			// Write headline to file
			writer.write(String.format("%-20s %s", "Token", "Lexeme\n"));
			writer.write("__________________________________\n");

			// Ouput each record to file
			for (String[] arr : records) {
				writer.write(String.format("%-20s %-20s \n", arr[0], arr[1]));
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("Creating new file or writer failed");
			e.printStackTrace();
		}
		System.out.println("Done");
	}


	// Receives list of values and hashsets containing identifiers. Outputs an arraylist of records containing [key,
	// value] pairs.
	public static ArrayList<String[]> lexer(ArrayList<String> values, HashSet<Character> seperators,
			HashSet<String> operators, HashSet<String> keywords) {
		ArrayList<String[]> records = new ArrayList<String[]>();

		// Iterate through each value in values
		for (String value : values) {
			String[] record = new String[2];
			String token = null;

			// Check if length of value is greater than 1.
			if (value.length() > 1) {
				if (keywords.contains(value)) // KEYWORD
					token = "keyword";
				else if (isNumeric(value)) // REAL
					token = "real";
				else if (operators.contains(value)) // OPERATOR
					token = "operator";
				else // IDENTIFIER
					token = "identifier";
			} // Value is one character
			else if (value.length() == 1) {
				if (seperators.contains(value.charAt(0))) // SEPERATOR
					token = "seperator";
				else if (operators.contains(value)) // OPERATOR
					token = "operator";
				else // IDENTIFIER
					token = "identifier";
			}

			// Add key-value pair to record
			record[0] = token;
			record[1] = value;

			// Add record to records
			records.add(record);
		}
		return records;
	}


	// Receives a string and outputs each value of the string, excludes whitespace.
	public static ArrayList<String> splitString(String str, HashSet<Character> seperators, HashSet<String> operators) {
		ArrayList<String> values = new ArrayList<String>();
		int wordCount = 0;
		boolean comment_flag = false;

		// Iterate through each character in the string.
		for (int i = 0; i < str.length(); i += wordCount) {
			if (comment_flag)
				break;

			StringBuilder sb = new StringBuilder();

			for (int j = i; j < str.length(); j++) {
				char curr = str.charAt(j);
				wordCount = j - i;

				// Commented line has been reached
				if (curr == '/' && str.charAt(i + 1) == '/') {
					comment_flag = true;
					break;
				}

				// Check if value is currently the start of an operator
				if (j + 1 < str.length() && operators
						.contains(String.valueOf(String.valueOf(curr) + String.valueOf(str.charAt(j + 1))))) {
					values.add(sb.append(curr).append(str.charAt(j + 1)).toString());
					wordCount += 2;
					break;
				}

				// Space has been reached. Ignore space.
				if (curr == ' ') {
					if (sb.toString().compareTo("") == 0) {
						wordCount++;
						break;
					}
					values.add(sb.toString());
					break;
				}

				// Character is a seperator or operator
				if (seperators.contains(curr) || operators.contains(String.valueOf(curr))) {
					if (sb.toString().length() > 0)
						values.add(sb.toString());
					values.add(String.valueOf(curr));
					wordCount++;
					break;
				} else {
					sb.append(curr);
				}
				if (wordCount == 0)
					wordCount = 1;
			}
		}
		return values;
	}


	// Receives a string and checks whether the string value is a valid float data type.
	public static boolean isNumeric(String str) {
		try {
			Float.parseFloat(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
