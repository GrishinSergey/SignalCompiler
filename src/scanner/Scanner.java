package scanner;

import exceptions.ScannerException;
import resources.DelimitersTable;
import resources.IdentifiersTable;
import resources.KeyWordsTable;
import resources.Token;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Scanner {

    private BufferedReader reader;
    private ArrayList<Token> tokens;

    public Scanner(String pathToFile) throws IOException {
        tokens = new ArrayList<>();
        reader = new BufferedReader(new FileReader(pathToFile));
    }

    public Scanner generateTokenList() throws IOException {
        int chr = reader.read(), lineNumber = 1, tokenCode;
        String token = "";
        do {
            try {
                if (isLetter(chr)) {
                    while (!isDelimiter(chr) && !isWhitespace(chr) && chr != -1) {
                        if (isLetter(chr) || Character.isDigit(chr)) {
                            token += (char) chr;
                            chr = reader.read();
                        }
                        else {
                            throw new ScannerException();
                        }
                    }
                    if ((tokenCode = KeyWordsTable.getInstance().getTokenCode(token)) != -1) {
                        addToken(tokenCode, lineNumber);
                    } else if ((tokenCode = IdentifiersTable.getInstance().getTokenCode(token)) != -1) {
                        addToken(tokenCode, lineNumber);
                    } else {
                        addToken(IdentifiersTable.getInstance().add(token), lineNumber);
                    }
                } else if (isDelimiter(chr)) {
                    if (chr != 40) {
                        addToken(DelimitersTable.getInstance().getTokenCode(Character.toString((char) chr)), lineNumber);
                        chr = reader.read();
                    }
                    else {
                        token = Character.toString((char) chr);
                        chr = reader.read();
                        if (chr == 42) {
                            addToken(DelimitersTable.getInstance().getTokenCode("(*"), lineNumber);
                            while (chr != -1) {
                                if (chr == 42) {
                                    chr = reader.read();
                                    if (chr == 41) {
                                        addToken(DelimitersTable.getInstance().getTokenCode("*)"), lineNumber);
                                        break;
                                    }
                                }
                                else {
                                    if (isNewLine(chr)) {
                                        lineNumber++;
                                    }
                                    chr = reader.read();
                                }
                            }
                            chr = reader.read();
                        }
                        else {
                            addToken(DelimitersTable.getInstance().getTokenCode(token), lineNumber);
                        }
                    }
                } else if (isWhitespace(chr)) {
                    while (isWhitespace(chr)) {
                        if (isNewLine(chr)) {
                            lineNumber++;
                        }
                        chr = reader.read();
                    }
                } else {
                    throw new ScannerException();
                }
            } catch (ScannerException se) {
                chr = readWhile(chr);
            }
            token = "";
        } while (chr != -1);
        reader.close();
        return this;
    }

    private int readWhile(int chr) throws IOException {
        while (!isDelimiter(chr) && !isWhitespace(chr) && chr != -1) {
            chr = reader.read();
        }
        return chr;
    }

    private boolean isNewLine(int chr) throws IOException {
        return chr == 10 || chr == 13 && reader.read() == 10 || chr == 13;
    }

    private boolean isLetter(int chr) {
        return chr >= 65 && chr <= 90;
    }

    private boolean isDelimiter(int chr) {
        return chr == 44 || chr == 58 || chr == 59 || chr == 40 || chr == 41;
    }

    private boolean isWhitespace(int chr) {
        return chr == 9 || chr == 32 || chr == 10 || chr == 13 || chr == 11;
    }

    private void addToken(int tokenCode, int lineNumber) {
        tokens.add(new Token(tokenCode, lineNumber));
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

}