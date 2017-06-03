import exceptions.ParserException;
import exceptions.ScannerException;
import parser.Parser;
import resources.token.ParserToken;
import scanner.Scanner;

import java.util.List;

public class Test {

    public static void main(String[] args) throws java.lang.Exception {
        String path = "/home/sergey/Java/projects/JavaNative/Compiler/SignalCompiler/src/tests/code/";
//        runTest(path, "declaration-procedure.sig");
//        runTest(path, "declaration-program.txt");
//        runTest(path, "without-declarations.sig");
        runTest(path, "error-asm-insert.sig");
        runTest(path, "error-declaration.txt");
        runTest(path, "error-function-declaration.txt");
        runTest(path, "error-incorrect-asm.txt");
        runTest(path, "error-label-declaration.txt");
        runTest(path, "error-unclosed-comment.txt");
    }

    private static void runTest(String path, String testName) {
        try {
            List<ParserToken> tree = new Parser(new Scanner(path + testName).runScanner().getTokens()).runParser();
//            new CodeGenerator(tree).runGenerator();
            System.out.println("correct test \"" + testName + "\" finished\n-----");
        } catch (java.io.IOException e) {
            System.out.println("file not found");
        } catch (ParserException | ScannerException e) {
            e.setFileName(testName);
            e.setClassName(testName);
            e.getException().printStackTrace();
        }
    }

}