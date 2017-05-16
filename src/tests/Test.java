package tests;

import exceptions.ParserException;
import exceptions.ScannerException;
import parser.Parser;
import scanner.Scanner;

import java.io.IOException;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        String path = "/home/sergey/Java/projects/JavaNative/Compiler/SignalCompiler/src/tests/code/";

        runTest(path, "declaration-procedure.sig");
        runTest(path, "declaration-program.txt");
        runTest(path, "without-declarations.sig");
        runTest(path, "error-asm-insert.sig");
        runTest(path, "error-declaration.txt");
        runTest(path, "error-function-declaration.txt");
        runTest(path, "error-incorrect-asm.txt");
        runTest(path, "error-label-declaration.txt");
        runTest(path, "error-unclosed-comment.txt");
    }

    private static void runTest(String path, String testName) {
        try {
            List tree = new Parser(new Scanner(path + testName).generateTokenList().getTokens()).runParser();
            System.out.println("correct test \"" + testName + "\" finished\n-----");
        } catch (IOException e) {
            System.out.println("file not found");
        } catch (ScannerException | ParserException e) {
            System.out.println("error test \"" + testName + "\" finished with message:");
            System.out.println(e.getMessage() + "\n-----");
        }
    }

}