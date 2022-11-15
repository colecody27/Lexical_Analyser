README

***Cody Cole | 885511774***
***Aidan Castillo | 12***

Description of how to use the program and expected input and ouput * Files Within Lexer Folder

• FSA_mydesign.doc - Contains the Finite State Automata design for the lexical analyzer.

• README.md - What you are reading now. • input_scode.txt - Contains the input for the lexer program that was provided in the project prompt.

• main.java - Contains the driver program and helper functions as well as the lexer function.

• output.txt - Contains the output of output of the lexer application. • main.class - Supporting file

INPUT

The input consists of a .txt file containing syntax from the programming language, C++. In this case, the input is only one line but the program is setup to handle multiple lines of code.

OUTPUT

The output is a record with columns, "Lexeme" and "value". Under Lexeme is the token that corresponds with the value found in the input. It is listed in the same row as it's lexeme. The possible lexemes are: • Seperator • Identifier • keyword • Operator • real

HOW TO USE

The program is currently setup to handle the input syntax of C++. To use this program, copy the lexer folder (or the 5 files listed above) over to an IDE of your choice. Do make sure that the "input_scode.txt" file is placed in your directory following the guidelines of your IDE. If the file is not found, an error will be thrown. From here the program is ready to be run. Once main.java is run, it will read the input file and output the lexemes and corresponding values to "output_file.txt". The program is setup to delete "output_file.txt" if it exists, if not it will create said file.

This project is setup to run within the bounds of the given problem provided in the prompt. If however, a user would like to expand the functionality or change the source code language, one change is necessary. The HashSets found in the beginning of the main program contain the possible lexemes, these need to be altered according to the language. Adding or removing these value from the Hash Sets will allow the lexer to identify the values with tokens.
