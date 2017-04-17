package tests;

import exceptions.ParserException;
import parser.Parser;
import scanner.Scanner;

import java.io.IOException;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        String path = "/home/sergey/Java/projects/Compiler/SignalCompiler/src/tests/code/parser-test.sig";
        try {
            List tree = new Parser(new Scanner(path).generateTokenList().getTokens()).runParser();
            System.out.println(tree);
        } catch (IOException e) {
            System.out.println("file not found");
        } catch (ParserException e) {
            System.out.println(e.getMessage());
        }
    }

}