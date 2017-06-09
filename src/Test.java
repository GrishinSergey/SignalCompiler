import exceptions.SemanticException;
import exceptions.ParserException;
import exceptions.ScannerException;
import generator.CodeGenerator;
import parser.Parser;
import resources.token.ParserToken;
import scanner.Scanner;
import semantic.SemanticAnalysis;

import java.util.List;

public class Test {

    public static void main(String[] args) throws java.lang.Exception {
        String path = "/home/sergey/Java/projects/JavaNative/Compiler/SignalCompiler/src/tests/code/";

        /** CorrectTests **/
//        runTest(path, "declaration-procedure.sig");
//        runTest(path, "declaration-program.txt");

        /** SemanticErrorTests **/
        runTest(path, "semantic-error-1.sig");
        runTest(path, "semantic-error-2.sig");
        runTest(path, "semantic-error-3.sig");
        runTest(path, "semantic-error-4.sig");
        runTest(path, "semantic-error-5.sig");
        runTest(path, "semantic-error-6.sig");

        /** ParserErrorTests **/
//        runTest(path, "error-asm-insert.sig");
//        runTest(path, "error-declaration.txt");
//        runTest(path, "error-function-declaration.txt");
//        runTest(path, "error-incorrect-asm.txt");
//        runTest(path, "error-label-declaration.txt");

        /** ScannerErrorTests **/
//        runTest(path, "error-unclosed-comment.txt");
//        runTest(path, "unexpectedsymbol.sig");
    }

    private static void runTest(String path, String testName) {
        try {
            List<ParserToken> tree = new Parser(new Scanner(path + testName)
                    .runScanner().getTokens())
                    .runParser();
//            List<ParserToken> optimisedTree =
            new SemanticAnalysis(tree)
                    .runSemanticAnalysis();
//                    .runDataFlowAnalysis()
//                    .getTree();
            String code = new CodeGenerator(tree).runGenerator().getResult();
            System.out.println(code);
            System.out.println("correct test \"" + testName + "\" finished\n-----");
        } catch (java.io.IOException e) {
            System.out.println("file not found");
        } catch (SemanticException | ParserException | ScannerException e) {
            e.setFileName(testName);
            e.getException().printStackTrace();
        }
    }

}