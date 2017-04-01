package utils;

import resources.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

public class Printer {

    private PrintStream ps = new PrintStream(new File("/home/sergey/Java/projects/SignalCompiler/src/tests/code/tests-result.txt"));

    public Printer() throws FileNotFoundException {}

    public void printTokenListWithTokens(ArrayList<Token> tokenList, String testName) {
        ps.println("--- START Test \"" + testName + "\" ---");
        for (Token token: tokenList) {
//            "Code: " + token.getCode() + " Line: " + token.getLineNumber() + " Token: " + getToken(token.getCode())
            ps.format("Line: %4d Code: %4d Token: %15s\n",
                    token.getLineNumber(),
                    token.getCode(),
                    getToken(token.getCode())
                    );
        }
        ps.println("--- END Test ---\n\n");
    }

    private String getToken(int tokenCode) {
        if (DelimitersTable.getInstance().getToken(tokenCode) != null) {
            return DelimitersTable.getInstance().getToken(tokenCode);
        }
        if (ConstTable.getInstance().getToken(tokenCode) != null) {
            return ConstTable.getInstance().getToken(tokenCode);
        }

        if (KeyWordsTable.getInstance().getToken(tokenCode) != null) {
            return KeyWordsTable.getInstance().getToken(tokenCode);
        }
        return IdentifiersTable.getInstance().getToken(tokenCode);
    }

}