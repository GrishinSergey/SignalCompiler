package parser.rules;

import exceptions.ParserException;
import resources.ErrorMessages;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class SingleStatement {

    public AssemblyInsertStatementToken getAssemblyInsertStatement(ScannerList scannerTokenList) throws ParserException {
        File file = new File(scannerTokenList.getToken());
        int lineNumber = scannerTokenList.getCurrentScannerToken().getLineNumber();
        if (!file.exists() || file.isDirectory()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_PATH_TO_ASSEMBLY_INSERT_FILE);
        }
        if (!Objects.equals(file.getName().substring(file.getName().lastIndexOf(".") + 1), "asm")) {
            throw new ParserException(ErrorMessages.UNEXPECTED_ASSEMBLY_INSERT_FILE);
        }
        if (9 != scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_END_OF_ASSEMBLY_INSERT);
        }
        return new AssemblyInsertStatementToken("asm", lineNumber, file.getAbsolutePath());
    }

    public LoopStatementToken getLoopStatement(ScannerList scannerTokenList) throws ParserException {
        LoopStatementToken loopStatementToken = new LoopStatementToken("loop",
                scannerTokenList.getCurrentScannerToken().getLineNumber(),
                new StatementsList().getStatementsList(scannerTokenList));
        if (116 != scannerTokenList.getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_END_OF_LOOP);
        }
        scannerTokenList.getRestOfScannerToken();
        if (5 != scannerTokenList.getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_END_OF_LINE);
        }
        return loopStatementToken;
    }

    public ProcedureCallStatementToken getProcedureCallStatement() {
        return null;
    }

    public GotoStatementToken getGotoStatement(ScannerList scannerTokenList) throws ParserException {
        if (500 > scannerTokenList.getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_INTEGER);
        }
        String value = scannerTokenList.getToken();
        scannerTokenList.getRestOfScannerToken();
        if (5 != scannerTokenList.getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_END_OF_LINE);
        }
        return new GotoStatementToken("goto", scannerTokenList.getPrevious().getLineNumber(), value);
    }

    public InStatementToken getInStatement(ScannerList scannerTokenList) throws ParserException {
        if (500 > scannerTokenList.getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_INTEGER);
        }
        String stream = scannerTokenList.getToken();
        scannerTokenList.getRestOfScannerToken();
        if (5 != scannerTokenList.getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_END_OF_LINE);
        }
        return new InStatementToken("in", scannerTokenList.getCurrentScannerToken().getLineNumber(), stream);
    }

    public OutStatementToken getOutStatement(ScannerList scannerTokenList) throws ParserException {
        if (500 > scannerTokenList.getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_INTEGER);
        }
        String stream = scannerTokenList.getToken();
        scannerTokenList.getRestOfScannerToken();
        if (5 != scannerTokenList.getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_END_OF_LINE);
        }
        return new OutStatementToken("out", scannerTokenList.getCurrentScannerToken().getLineNumber(), stream);
    }

    public LinkStatementToken getLinkStatement(ScannerList scannerTokenList) throws ParserException {
        if (200 > scannerTokenList.getCurrentScannerToken().getCode() ||
                500 < scannerTokenList.getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_VARIABLE);
        }
        String variable = scannerTokenList.getToken();
        scannerTokenList.getRestOfScannerToken();
        if (7 != scannerTokenList.getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_DELIMITER);
        }
        scannerTokenList.getRestOfScannerToken();
        if (500 > scannerTokenList.getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_INTEGER);
        }
        String value = scannerTokenList.getToken();
        scannerTokenList.getRestOfScannerToken();
        if (5 != scannerTokenList.getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_END_OF_LINE);
        }
        return new LinkStatementToken("link", scannerTokenList.getPrevious().getLineNumber(), variable, value);
    }

    public ReturnStatementToken getReturnStatement(ScannerList scannerTokenList) throws ParserException {
        if (5 != scannerTokenList.getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_END_OF_LINE);
        }
        return new ReturnStatementToken("return", scannerTokenList.getCurrentScannerToken().getLineNumber());
    }

    private class AssemblyInsertStatementToken extends ParserToken {

        private String pathToAsmFile;

        AssemblyInsertStatementToken(String token, int line, String pathToAsmFile) {
            super(token, line);
            this.pathToAsmFile = pathToAsmFile;
        }

    }

    private class LoopStatementToken extends ParserToken {

        private List<ParserToken> statements;

        LoopStatementToken(String token, int line, List<ParserToken> statements) {
            super(token, line);
            this.statements = statements;
        }

    }

    private class ProcedureCallStatementToken extends ParserToken {

        private String procedureName;
        private List<ParserToken> parameters;

        ProcedureCallStatementToken(String token, int line, String procedureName) {
            super(token, line);
            this.procedureName = procedureName;
        }

        public boolean add(ParserToken param) {
            return parameters.add(param);
        }

    }

    private class GotoStatementToken extends ParserToken {

        private String labelIdentifier;

        GotoStatementToken(String token, int line, String labelIdentifier) {
            super(token, line);
            this.labelIdentifier = labelIdentifier;
        }

    }

    private class InStatementToken extends ParserToken {

        private String inSteam;

        InStatementToken(String token, int line, String inSteam) {
            super(token, line);
            this.inSteam = inSteam;
        }

    }

    private class OutStatementToken extends ParserToken {

        private String outSteam;

        OutStatementToken(String token, int line, String outSteam) {
            super(token, line);
            this.outSteam = outSteam;
        }

    }

    private class LinkStatementToken extends ParserToken {

        private String variable;
        private String value;

        LinkStatementToken(String token, int line, String variable, String value) {
            super(token, line);
            this.variable = variable;
            this.value = value;
        }

    }

    private class ReturnStatementToken extends ParserToken {

        ReturnStatementToken(String token, int line) {
            super(token, line);
        }

    }

}
