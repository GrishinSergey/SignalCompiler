package parser;

import exceptions.ParserException;
import parser.rules.Program;
import resources.ErrorMessages;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.util.List;

public class Parser {

    private ScannerList scannerTokenList;

    public Parser(ScannerList scannerTokenList) {
        this.scannerTokenList = scannerTokenList;
    }

    public List<ParserToken> runParser() throws ParserException {
        /* @TODO: check, is it need to get previous token? Can use current? */
        try {
            return new Program(scannerTokenList).getProgram();
        } catch (ParserException e) {
            ParserException pe = new ParserException(e.getMessage());
            int lineNumber = e.getLine() > 0 ? e.getLine(): scannerTokenList.getCurrentScannerToken().getLine();
            pe.setLine(lineNumber);
            throw pe;
        } catch (IndexOutOfBoundsException e) {
            throw new ParserException(ErrorMessages.UNEXPECTED_END_OF_FILE);
        }
    }

}