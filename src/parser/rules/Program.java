package parser.rules;


import exceptions.ParserException;
import resources.ErrorMessages;
import resources.token.ParserToken;
import resources.token.ScannerToken;
import scanner.ScannerList;

import java.util.ArrayList;
import java.util.List;

public class Program extends AbstractRule {

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
        throwExceptionIfUnexpectedIdentifier(nextToken.getCode(), ErrorMessages.UNEXPECTED_PROGRAM_NAME);
        res.add(new ProgramToken("program", token.getLineNumber(), scannerTokenList.getToken()));
        throwExceptionIfUnexpectedEndOfLine(scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode());
        res.add(new Block().getBlock(new DeclarationsList().getDeclarationsList(scannerTokenList.getRestOfScannerToken()), scannerTokenList));
        if (10 != scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_END_OF_FILE);
        }
        return res;
    }

    private List procedure() throws ParserException {
        List<ParserToken> res = new ArrayList<>();
        res.add(new ProcedureDeclarations().getProceduresDeclarations(scannerTokenList).get(0));
        res.add(new Block().getBlock(new DeclarationsList().getDeclarationsList(scannerTokenList), scannerTokenList));
        return res;
    }

    private class ProgramToken extends ParserToken {

        private String name;

        ProgramToken(String token, int line, String name) {
            super(token, line);
            this.name = name;
        }

    }

    static class ProcedureToken extends ParserToken {

        private String name;
        private List<String> params;

        ProcedureToken(String token, int line, String name, List<String> params) {
            super(token, line);
            this.name = name;
            this.params = params;
        }

    }

}
