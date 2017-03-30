package tests;

import scanner.Scanner;
import utils.Printer;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Test {

    private static Printer printer;

    public static void main(String[] args) {
        runTest("Cyrillic test", "/home/sergey/Java/projects/SignalCompiler/src/tests/code/cyrillic.sig");
        runTest("Whitespace test", "/home/sergey/Java/projects/SignalCompiler/src/tests/code/whitespaces.sig");
        runTest("Usual comment test", "/home/sergey/Java/projects/SignalCompiler/src/tests/code/comments-1.sig");
        runTest("Non-closed comments test", "/home/sergey/Java/projects/SignalCompiler/src/tests/code/comments-2.sig");
        runTest("Without spaces comment test", "/home/sergey/Java/projects/SignalCompiler/src/tests/code/comments-3.sig");
        runTest("Correct test", "/home/sergey/Java/projects/SignalCompiler/src/tests/code/correct-test.sig");
        runTest("Errors contains test", "/home/sergey/Java/projects/SignalCompiler/src/tests/code/errors-contains-test.sig");
    }

    private static void runTest(String testName, String filePath) {
        try {
            if (printer == null) {
                printer = new Printer();
            }
            printer.printTokenListWithTokens(new Scanner(filePath).generateTokenList().getTokens(), testName);
        } catch (FileNotFoundException fnfe) {
            System.out.println("Tests out-file not found");
        } catch (IOException ioe) {
            System.out.println("File \"" + testName + "\" with test not found or another exception cached");
        }
    }

}