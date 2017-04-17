package parser.rules;


import exceptions.ParserException;
import resources.ErrorMessages;
import resources.token.ParserToken;
import resources.token.ScannerToken;
import scanner.ScannerList;

import java.util.ArrayList;
import java.util.List;

public class Program {

    private ScannerList scannerTokenList;

    public Program(ScannerList scannerTokenList) {
        this.scannerTokenList = scannerTokenList;
    }

    public List getProgram() throws ParserException {
        if (110 == scannerTokenList.getCurrentScannerToken().getCode()) {
            return program();
        }
        if (101 == scannerTokenList.getCurrentScannerToken().getCode()) {
            return procedure();
        }
        throw new ParserException(ErrorMessages.UNEXPECTED_BEGIN_OF_PROGRAM);
    }

    private List program() throws ParserException {
        List<ParserToken> res = new ArrayList<>();
        ScannerToken token = scannerTokenList.getCurrentScannerToken(),
                nextToken = scannerTokenList.getRestOfScannerToken().getCurrentScannerToken();
        if (200 > nextToken.getCode() || 500 <= nextToken.getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_PROGRAM_NAME);
        }
        res.add(new ProgramToken("program", token.getLineNumber(), scannerTokenList.getToken()));
        if (5 != scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_END_OF_LINE);
        }
        res.add(new Block().getBlock(scannerTokenList.getRestOfScannerToken()));
        if (10 != scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_END_OF_FILE);
        }
        return res;
    }

    private List procedure() {
        return null;
    }

    private class ProgramToken extends ParserToken {

        private String name;

        ProgramToken(String token, int line, String name) {
            super(token, line);
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

    private class ProcedureToken extends ParserToken {

        private String name;
        private List<ParserToken> params;

        public ProcedureToken(String token, int line, String name, List<ParserToken> params) {
            super(token, line);
            this.name = name;
            this.params = params;
        }

        public String getName() {
            return name;
        }

        public List<ParserToken> getParams() {
            return params;
        }
    }

}
