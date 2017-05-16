package parser;

import exceptions.ParserException;
import parser.rules.Program;
import resources.ErrorMessages;
import scanner.ScannerList;

import java.util.List;

public class Parser {

    private ScannerList scannerTokenList;

    public Parser(ScannerList scannerTokenList) {
        this.scannerTokenList = scannerTokenList;
    }

    public List runParser() throws ParserException {
        try {
            return new Program(scannerTokenList).getProgram();
        } catch (ParserException | IndexOutOfBoundsException e) {
            /* @TODO: check, is it need to get previous token? Can use current? */
            String errorMessage;
            int lineNumber = (e instanceof ParserException && ((ParserException) e).getLineNumber() > 0) ?
                    ((ParserException) e).getLineNumber() :
                    scannerTokenList.getCurrentScannerToken().getLineNumber();
            if (e instanceof IndexOutOfBoundsException) {
                errorMessage = ErrorMessages.UNEXPECTED_END_OF_FILE + "on line " + lineNumber;
            }
            else {
                errorMessage = e.getMessage() + "on line " + lineNumber;
            }
            throw new ParserException(errorMessage);
        }
    }

}
