package scanner;


import exceptions.ScannerException;
import resources.ErrorMessages;
import resources.tables.scannertables.ConstScannerTable;
import resources.tables.scannertables.DelimitersScannerTable;
import resources.tables.scannertables.IdentifiersScannerTable;
import resources.tables.scannertables.KeyWordsScannerTable;
import resources.token.ScannerToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Scanner {

    private BufferedReader reader;
    private ArrayList<ScannerToken> tokens;
    private int chr;
    private int lineNumber;

    public Scanner(String pathToFile) throws IOException {
        tokens = new ArrayList<>();
        reader = new BufferedReader(new FileReader(pathToFile));
        chr = reader.read();
        lineNumber = 1;
    }

    public Scanner runScanner() throws IOException, ScannerException {
        int tokenCode;
        String token;
        do {
            if (isLetter()) {
                token = getIdentifier();
                if ((tokenCode = KeyWordsScannerTable.getInstance().getTokenCode(token)) != -1) {
                    addToken(tokenCode);
                }
                else {
                    addIdentifier(token);
                }
            }
            else if (Character.isDigit(chr)) {
                token = "";
                while (Character.isDigit(chr)) {
                    token += (char) chr;
                    chr = reader.read();
                }
                if ((tokenCode = ConstScannerTable.getInstance().getTokenCode(token)) != -1) {
                    addToken(tokenCode);
                }
                else {
                    addToken(ConstScannerTable.getInstance().add(token));
                }
            }
            else if (isDelimiter()) {
                if (chr == 40) {
                    token = Character.toString((char) chr);
                    chr = reader.read();
                    if (chr == 42) {
                        getComment();
                    }
                    else if (chr == 36) {
                        token += (char) chr;
                        chr = reader.read();
                        addToken(DelimitersScannerTable.getInstance().getTokenCode(token));
                        readWhileWhitespace();
                        token = "";
                        while (!isWhitespace() && chr != -1) {
                            token += (char) chr;
                            if (isNewLine()) {
                                lineNumber++;
                            }
                            chr = reader.read();
                        }
                        addIdentifier(token);
                        readWhileWhitespace();
                        token = "" + (char) chr;
                        chr = reader.read();
                        if (isNewLine()) {
                            lineNumber++;
                        }
                        token += (char) chr;
                        addToken(DelimitersScannerTable.getInstance().getTokenCode(token));
                        chr = reader.read();
                    }
                    else {
                        addToken(DelimitersScannerTable.getInstance().getTokenCode(token));
                    }
                }
                else {
                    addToken(DelimitersScannerTable.getInstance().getTokenCode(Character.toString((char) chr)));
                    chr = reader.read();
                }
            }
            else if (isWhitespace()) {
                readWhileWhitespace();
            }
            else {
                throwNewScannerException(ErrorMessages.UNEXPECTED_TOKEN, lineNumber);
            }
        } while (chr != -1);
        reader.close();
        return this;
    }

    private void addIdentifier(String token) {
        int tokenCode;
        if ((tokenCode = IdentifiersScannerTable.getInstance().getTokenCode(token)) != -1) {
            addToken(tokenCode);
        }
        else {
            addToken(IdentifiersScannerTable.getInstance().add(token));
        }
    }

    private void readWhileWhitespace() throws IOException {
        while (isWhitespace()) {
            if (isNewLine()) {
                lineNumber++;
            }
            chr = reader.read();
        }
    }

    private String getIdentifier() throws ScannerException, IOException {
        String token = "";
        while (!isDelimiter() && !isWhitespace() && chr != -1) {
            if (isLetter() || Character.isDigit(chr)) {
                token += (char) chr;
                chr = reader.read();
            }
            else {
                throw new ScannerException();
            }
        }
        return token;
    }

    private void getComment() throws IOException, ScannerException {
        boolean closedCommentFlag = false;
        int firstCommentLine = lineNumber;
        while (chr != -1) {
            if (chr == 42) {
                chr = reader.read();
                if (chr == 41) {
                    closedCommentFlag = true;
                    break;
                }
            }
            else {
                if (isNewLine()) {
                    lineNumber++;
                }
                chr = reader.read();
            }
        }
        if (!closedCommentFlag) {
            throwNewScannerException(ErrorMessages.UNCLOSED_COMMENT, firstCommentLine);
        }
        chr = reader.read();
    }

    private ScannerException throwNewScannerException(String message, int lineNumber) throws ScannerException {
        ScannerException se = new ScannerException(message);
        se.setLine(lineNumber);
        throw se;

    }

    private boolean isNewLine() throws IOException {
        return chr == 10 || chr == 13 && reader.read() == 10 || chr == 13;
    }

    private boolean isLetter() {
        return chr >= 65 && chr <= 90;
    }

    private boolean isDelimiter() {
        return chr == 44 || chr == 58 || chr == 59 || chr == 40 || chr == 41 || chr == 46 || chr == 61 || chr == 92;
    }

    private boolean isWhitespace() {
        return chr == 9 || chr == 32 || chr == 10 || chr == 13 || chr == 11;
    }

    private void addToken(int tokenCode) {
        tokens.add(new ScannerToken(tokenCode, lineNumber));
    }

    public ScannerList getTokens() {
        return new ScannerList(tokens);
    }

}
