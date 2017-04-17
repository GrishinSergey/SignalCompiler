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
            String errorMessage;
            if (e instanceof IndexOutOfBoundsException) {
                errorMessage = ErrorMessages.UNEXPECTED_END_OF_FILE + "on line: " + scannerTokenList.getPrevious().getLineNumber();
            }
            else {
                errorMessage = e.getMessage() + "on line: " + scannerTokenList.getPrevious().getLineNumber();
            }
            throw new ParserException(errorMessage);
        }
    }

}
