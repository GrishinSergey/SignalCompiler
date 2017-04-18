package parser.rules;

import exceptions.ParserException;
import resources.ErrorMessages;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.io.File;
import java.util.List;
import java.util.Objects;

class SingleStatement extends AbstractRule {

    AssemblyInsertStatementToken getAssemblyInsertStatement(ScannerList scannerTokenList) throws ParserException {
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

    LabelStatementToken getLabelStatement(ScannerList scannerTokenList) throws ParserException {
        String labelIdentifier = scannerTokenList.getToken();
        int lineNumber = scannerTokenList.getCurrentScannerToken().getLineNumber();
        scannerTokenList.getRestOfScannerToken();
        if (6 != scannerTokenList.getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_PROCEDURE_CALL);
        }
        return new LabelStatementToken("label", lineNumber, labelIdentifier,
                new StatementsList().getStatement(
                        scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode(),
                        scannerTokenList));
    }

    LoopStatementToken getLoopStatement(ScannerList scannerTokenList) throws ParserException {
        LoopStatementToken loopStatementToken = new LoopStatementToken("loop",
                scannerTokenList.getPrevious().getLineNumber(),
                new StatementsList().getStatementsList(scannerTokenList));
        if (116 != scannerTokenList.getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_END_OF_LOOP);
        }
        scannerTokenList.getRestOfScannerToken();
        throwExceptionIfUnexpectedEndOfLine(scannerTokenList.getCurrentScannerToken().getCode());
        return loopStatementToken;
    }

    ProcedureCallStatementToken getProcedureCallStatement(ScannerList scannerTokenList) throws ParserException {
        ProcedureCallStatementToken procedureCallStatementToken = null;
        if (1 == scannerTokenList.getCurrentScannerToken().getCode()) {
            procedureCallStatementToken = new ProcedureCallStatementToken("procedurecall",
                    scannerTokenList.getCurrentScannerToken().getLineNumber(),
                    scannerTokenList.getToken(scannerTokenList.getPrevious().getCode()),
                    new IdentifiersList().getIdentifiersList(scannerTokenList.getRestOfScannerToken()));
            if (2 != scannerTokenList.getCurrentScannerToken().getCode()) {
                throw new ParserException(ErrorMessages.UNEXPECTED_PROCEDURE_CALL);
            }
            scannerTokenList.getRestOfScannerToken();
        }
        throwExceptionIfUnexpectedEndOfLine(scannerTokenList.getCurrentScannerToken().getCode());
        if (procedureCallStatementToken == null) {
            procedureCallStatementToken = new ProcedureCallStatementToken("procedurecall",
                    scannerTokenList.getCurrentScannerToken().getLineNumber(),
                    scannerTokenList.getToken(scannerTokenList.getPrevious().getCode()), null);
        }
        return procedureCallStatementToken;
    }

    GotoStatementToken getGotoStatement(ScannerList scannerTokenList) throws ParserException {
        throwExceptionIfUnexpectedUnsignedInteger(scannerTokenList.getCurrentScannerToken().getCode());
        String value = scannerTokenList.getToken();
        scannerTokenList.getRestOfScannerToken();
        throwExceptionIfUnexpectedEndOfLine(scannerTokenList.getCurrentScannerToken().getCode());
        return new GotoStatementToken("goto", scannerTokenList.getPrevious().getLineNumber(), value);
    }

    InStatementToken getInStatement(ScannerList scannerTokenList) throws ParserException {
        throwExceptionIfUnexpectedUnsignedInteger(scannerTokenList.getCurrentScannerToken().getCode());
        String stream = scannerTokenList.getToken();
        scannerTokenList.getRestOfScannerToken();
        throwExceptionIfUnexpectedEndOfLine(scannerTokenList.getCurrentScannerToken().getCode());
        return new InStatementToken("in", scannerTokenList.getCurrentScannerToken().getLineNumber(), stream);
    }

    OutStatementToken getOutStatement(ScannerList scannerTokenList) throws ParserException {
        throwExceptionIfUnexpectedUnsignedInteger(scannerTokenList.getCurrentScannerToken().getCode());
        String stream = scannerTokenList.getToken();
        scannerTokenList.getRestOfScannerToken();
        throwExceptionIfUnexpectedEndOfLine(scannerTokenList.getCurrentScannerToken().getCode());
        return new OutStatementToken("out", scannerTokenList.getCurrentScannerToken().getLineNumber(), stream);
    }

    LinkStatementToken getLinkStatement(ScannerList scannerTokenList) throws ParserException {
        throwExceptionIfUnexpectedIdentifier(scannerTokenList.getCurrentScannerToken().getCode());
        String variable = scannerTokenList.getToken();
        scannerTokenList.getRestOfScannerToken();
        if (7 != scannerTokenList.getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_DELIMITER);
        }
        scannerTokenList.getRestOfScannerToken();
        throwExceptionIfUnexpectedUnsignedInteger(scannerTokenList.getCurrentScannerToken().getCode());
        String value = scannerTokenList.getToken();
        scannerTokenList.getRestOfScannerToken();
        throwExceptionIfUnexpectedEndOfLine(scannerTokenList.getCurrentScannerToken().getCode());
        return new LinkStatementToken("link", scannerTokenList.getPrevious().getLineNumber(), variable, value);
    }

    ReturnStatementToken getReturnStatement(ScannerList scannerTokenList) throws ParserException {
        throwExceptionIfUnexpectedEndOfLine(scannerTokenList.getCurrentScannerToken().getCode());
        return new ReturnStatementToken("return", scannerTokenList.getCurrentScannerToken().getLineNumber());
    }

    private class LabelStatementToken extends ParserToken {

        private String labelIdentifier;
        private ParserToken statement;

        LabelStatementToken(String token, int line, String labelIdentifier, ParserToken statement) {
            super(token, line);
            this.labelIdentifier = labelIdentifier;
            this.statement = statement;
        }

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

        ProcedureCallStatementToken(String token, int line, String procedureName, List<ParserToken> parameters) {
            super(token, line);
            this.procedureName = procedureName;
            this.parameters = parameters;
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
