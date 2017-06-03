package parser.rules;

import exceptions.ParserException;
import resources.ErrorMessages;
import resources.tables.parsertables.ProceduresParserTable;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.util.ArrayList;
import java.util.List;

public class ProcedureDeclarations extends AbstractRule {

    List<ParserToken> getProceduresDeclarations(ScannerList scannerTokenList, String ruleName) throws ParserException {
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
                            throw new ParserException(ErrorMessages.UNEXPECTED_ATTRIBUTE_IN_PROCEDURE_DECLARATION);
                        }
                        flag = false;
                    } else if (isAttribute(scannerTokenList.getCurrentScannerToken().getCode())) {
                        attributes.add(scannerTokenList.getToken());
                        if (flag) {
                            throw new ParserException(ErrorMessages.UNEXPECTED_ATTRIBUTE_IN_PROCEDURE_DECLARATION);
                        }
                        flag = true;
                    } else {
                        throw new ParserException(ErrorMessages.UNEXPECTED_ATTRIBUTE_IN_PROCEDURE_DECLARATION);
                    }
                    scannerTokenList.getRestOfScannerToken();
                }
                procedureToken = new Program.ProcedureToken(ruleName, lineNumber, procedureName, attributes);
                if (2 != scannerTokenList.getCurrentScannerToken().getCode()) {
                    throw new ParserException(ErrorMessages.UNEXPECTED_END_OF_ATTRIBUTE_LIST);
                }
                scannerTokenList.getRestOfScannerToken();
            }
            throwExceptionIfUnexpectedEndOfLine(scannerTokenList.getCurrentScannerToken().getCode());
            if (procedureToken == null) {
                procedureToken = new Program.ProcedureToken(ruleName, lineNumber, procedureName, null);
            }
            res.add(procedureToken);
            ProceduresParserTable.getInstance().add(procedureToken);
            scannerTokenList.getRestOfScannerToken();
        }
        return res;
    }

}
