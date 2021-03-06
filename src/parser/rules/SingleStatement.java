package parser.rules;

import exceptions.ParserException;
import resources.ErrorMessages;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SingleStatement extends AbstractRule {

    AssemblyInsertStatementToken getAssemblyInsertStatement(ScannerList scannerTokenList) throws ParserException {
        File file = new File(scannerTokenList.getToken());
        int lineNumber = scannerTokenList.getCurrentScannerToken().getLine();
        if (!file.exists() || file.isDirectory()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_PATH_TO_ASSEMBLY_INSERT_FILE);
        }
        if (!Objects.equals(file.getName().substring(file.getName().lastIndexOf(".") + 1), "asm")) {
            throw new ParserException(ErrorMessages.UNEXPECTED_ASSEMBLY_INSERT_FILE);
        }
        if (9 != scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode()) {
            throw new ParserException(ErrorMessages.UNEXPECTED_END_OF_ASSEMBLY_INSERT,
                    scannerTokenList.getPrevious().getLine());
        }
        return new AssemblyInsertStatementToken("asm", lineNumber, file.getAbsolutePath());
    }

    LabelStatementToken getLabelStatement(ScannerList scannerTokenList) throws ParserException {
        String labelIdentifier = scannerTokenList.getToken();
        int lineNumber = scannerTokenList.getCurrentScannerToken().getLine();
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
                scannerTokenList.getPrevious().getLine(),
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
                    scannerTokenList.getCurrentScannerToken().getLine(),
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
                    scannerTokenList.getCurrentScannerToken().getLine(),
                    scannerTokenList.getToken(scannerTokenList.getPrevious().getCode()),
                    new ArrayList<>());
        }
        return procedureCallStatementToken;
    }

    GotoStatementToken getGotoStatement(ScannerList scannerTokenList) throws ParserException {
        throwExceptionIfUnexpectedUnsignedInteger(scannerTokenList.getCurrentScannerToken().getCode());
        String value = scannerTokenList.getToken();
        scannerTokenList.getRestOfScannerToken();
        throwExceptionIfUnexpectedEndOfLine(scannerTokenList.getCurrentScannerToken().getCode());
        return new GotoStatementToken("goto", scannerTokenList.getPrevious().getLine(), value);
    }

    InStatementToken getInStatement(ScannerList scannerTokenList) throws ParserException {
        throwExceptionIfUnexpectedUnsignedInteger(scannerTokenList.getCurrentScannerToken().getCode());
        String stream = scannerTokenList.getToken();
        scannerTokenList.getRestOfScannerToken();
        throwExceptionIfUnexpectedEndOfLine(scannerTokenList.getCurrentScannerToken().getCode());
        return new InStatementToken("in", scannerTokenList.getCurrentScannerToken().getLine(), stream);
    }

    OutStatementToken getOutStatement(ScannerList scannerTokenList) throws ParserException {
        throwExceptionIfUnexpectedUnsignedInteger(scannerTokenList.getCurrentScannerToken().getCode());
        String stream = scannerTokenList.getToken();
        scannerTokenList.getRestOfScannerToken();
        throwExceptionIfUnexpectedEndOfLine(scannerTokenList.getCurrentScannerToken().getCode());
        return new OutStatementToken("out", scannerTokenList.getCurrentScannerToken().getLine(), stream);
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
        return new LinkStatementToken("link", scannerTokenList.getPrevious().getLine(), variable, value);
    }

    ReturnStatementToken getReturnStatement(ScannerList scannerTokenList) throws ParserException {
        throwExceptionIfUnexpectedEndOfLine(scannerTokenList.getCurrentScannerToken().getCode());
        return new ReturnStatementToken("return", scannerTokenList.getCurrentScannerToken().getLine());
    }

    public class LabelStatementToken extends ParserToken {

        private ParserToken statement;
        private String labelIdentifier;

        LabelStatementToken(String token, int line, String labelIdentifier, ParserToken statement) {
            super(token, line);
            this.labelIdentifier = labelIdentifier;
            this.statement = statement;
        }

        public ParserToken getStatement() {
            return statement;
        }

        public String getLabelIdentifier() {
            return labelIdentifier;
        }

    }

    public class AssemblyInsertStatementToken extends ParserToken {

        private String pathToAsmFile;

        AssemblyInsertStatementToken(String token, int line, String pathToAsmFile) {
            super(token, line);
            this.pathToAsmFile = pathToAsmFile;
        }

        public String getPathToAsmFile() {
            return pathToAsmFile;
        }

    }

    public class LoopStatementToken extends ParserToken {

        private List<ParserToken> statements;

        LoopStatementToken(String token, int line, List<ParserToken> statements) {
            super(token, line);
            this.statements = statements;
        }

        public List<ParserToken> getStatements() {
            return statements;
        }

    }

    public class ProcedureCallStatementToken extends ParserToken {

        private String procedureName;
        private List<ParserToken> parameters;

        ProcedureCallStatementToken(String token, int line, String procedureName, List<ParserToken> parameters) {
            super(token, line);
            this.procedureName = procedureName;
            this.parameters = parameters;
        }

        public List<ParserToken> getParameters() {
            return parameters;
        }

        public String getProcedureName() {
            return procedureName;
        }
    }

    public class GotoStatementToken extends ParserToken {

        private String labelIdentifier;

        GotoStatementToken(String token, int line, String labelIdentifier) {
            super(token, line);
            this.labelIdentifier = labelIdentifier;
        }

        public String getLabelIdentifier() {
            return labelIdentifier;
        }

    }

    public class InStatementToken extends ParserToken {

        private String inSteam;

        InStatementToken(String token, int line, String inSteam) {
            super(token, line);
            this.inSteam = inSteam;
        }

        public String getInSteam() {
            return inSteam;
        }

    }

    public class OutStatementToken extends ParserToken {

        private String outSteam;

        OutStatementToken(String token, int line, String outSteam) {
            super(token, line);
            this.outSteam = outSteam;
        }

        public String getOutSteam() {
            return outSteam;
        }

    }

    public class LinkStatementToken extends ParserToken {

        private String variable;
        private String value;

        LinkStatementToken(String token, int line, String variable, String value) {
            super(token, line);
            this.variable = variable;
            this.value = value;
        }

        public String getVariable() {
            return variable;
        }

        public String getValue() {
            return value;
        }

    }

    public class ReturnStatementToken extends ParserToken {

        ReturnStatementToken(String token, int line) {
            super(token, line);
        }

    }

}
