package Lexer;

import java.util.*;
import java.util.Scanner;
import java.io.*;

public class main {

	// Driver program to use lexer class
	public static void main(String[] args) {
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

		// Iterate through each line in the text
		while (scanFile.hasNextLine()) {
			String line = scanFile.nextLine();
			ArrayList<String> subStrings = new ArrayList<String>();
			int substring_count = 0;

			// Split string on spaces, can't use regex
			for (int i = 0; i < line.length(); i += substring_count) {
				substring_count = 0;
				StringBuilder sb = new StringBuilder();
				int j = i;
				while (j < line.length()) {
					if (line.charAt(j) != ' ') {
						subStrings.add(sb.toString());
						substring_count++;
						break;
					} else
						sb.append(line.charAt(j));
					substring_count++;
				}
			}

			for (String word : subStrings) {
				System.out.println(word);
			}

		}

	}

	// Input -> String of source code in C++
	// Output -> Output to console -> Two columns[token - lexeme]
	// public static List<String[]> lexer(String line) {
	// List<String[]> tokens = new ArrayList<String[]>();
	//
	// }
}

