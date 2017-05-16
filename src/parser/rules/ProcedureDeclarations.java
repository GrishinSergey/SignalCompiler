package parser.rules;

import exceptions.ParserException;
import resources.tables.parsertables.ProceduresParserTable;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.util.ArrayList;
import java.util.List;

class ProcedureDeclarations extends AbstractRule {

    List<ParserToken> getProceduresDeclarations(ScannerList scannerTokenList) throws ParserException {
        List<ParserToken> res = new ArrayList<>();
        while (scannerTokenList.isNotEnded() &&
                102 != scannerTokenList.getCurrentScannerToken().getCode() &&
                109 != scannerTokenList.getCurrentScannerToken().getCode() &&
                111 != scannerTokenList.getCurrentScannerToken().getCode() &&
                103 != scannerTokenList.getCurrentScannerToken().getCode()) {
            scannerTokenList.getRestOfScannerToken();
            throwExceptionIfUnexpectedIdentifier(scannerTokenList.getCurrentScannerToken().getCode());
            int lineNumber = scannerTokenList.getCurrentScannerToken().getLineNumber();
            String procedureName = scannerTokenList.getToken();
            Program.ProcedureToken procedureToken = null;
            scannerTokenList.getRestOfScannerToken();
            if (1 == scannerTokenList.getCurrentScannerToken().getCode()) {
                List<String> attributes = new ArrayList<>();
                scannerTokenList.getRestOfScannerToken();
                boolean flag = false;
                while (scannerTokenList.isNotEnded() &&
                        (isAttribute(scannerTokenList.getCurrentScannerToken().getCode()) ||
                        7 == scannerTokenList.getCurrentScannerToken().getCode())) {
                    if (7 == scannerTokenList.getCurrentScannerToken().getCode()) {
                        if (!flag) {
                            throw new ParserException();
                        }
                        flag = false;
                    } else if (isAttribute(scannerTokenList.getCurrentScannerToken().getCode())) {
                        attributes.add(scannerTokenList.getToken());
                        if (flag) {
                            throw new ParserException("Unexpected attribute ");
                        }
                        flag = true;
                    } else {
                        throw new ParserException("Unexpected attribute ");
                    }
                    scannerTokenList.getRestOfScannerToken();
                }
                procedureToken = new Program.ProcedureToken("proceduredef", lineNumber, procedureName, attributes);
                if (2 != scannerTokenList.getCurrentScannerToken().getCode()) {
                    throw new ParserException("Expected ) ");
                }
                scannerTokenList.getRestOfScannerToken();
            }
            throwExceptionIfUnexpectedEndOfLine(scannerTokenList.getCurrentScannerToken().getCode());
            if (procedureToken == null) {
                procedureToken = new Program.ProcedureToken("proceduredef", lineNumber, procedureName, null);
            }
            res.add(procedureToken);
            ProceduresParserTable.getInstance().add(procedureToken);
            scannerTokenList.getRestOfScannerToken();
        }
        return res;
    }

}
