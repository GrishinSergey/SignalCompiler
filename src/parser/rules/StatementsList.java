package parser.rules;

import exceptions.ParserException;
import resources.ErrorMessages;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.util.ArrayList;
import java.util.List;

class StatementsList extends AbstractRule {

    List<ParserToken> getStatementsList(ScannerList scannerTokenList) throws ParserException {
        List<ParserToken> res = new ArrayList<>();
        SingleStatement statement = new SingleStatement();
        while (scannerTokenList.isNotEnded() &&
                108 != scannerTokenList.getCurrentScannerToken().getCode() &&
                116 != scannerTokenList.getCurrentScannerToken().getCode()) {

            if (114 == scannerTokenList.getCurrentScannerToken().getCode()) {
                res.add(statement.getLinkStatement(scannerTokenList.getRestOfScannerToken()));
            } else if (117 == scannerTokenList.getCurrentScannerToken().getCode()) {
                res.add(statement.getGotoStatement(scannerTokenList.getRestOfScannerToken()));
            } else if (115 == scannerTokenList.getCurrentScannerToken().getCode()) {
                res.add(statement.getLoopStatement(scannerTokenList.getRestOfScannerToken()));
            } else if (118 == scannerTokenList.getCurrentScannerToken().getCode()) {
                res.add(statement.getReturnStatement(scannerTokenList.getRestOfScannerToken()));
            } else if (8 == scannerTokenList.getCurrentScannerToken().getCode()) {
                res.add(statement.getAssemblyInsertStatement(scannerTokenList.getRestOfScannerToken()));
            } else if (112 == scannerTokenList.getCurrentScannerToken().getCode()) {
                res.add(statement.getInStatement(scannerTokenList.getRestOfScannerToken()));
            } else if (113 == scannerTokenList.getCurrentScannerToken().getCode()) {
                res.add(statement.getOutStatement(scannerTokenList.getRestOfScannerToken()));
            } else if (5 == scannerTokenList.getCurrentScannerToken().getCode()) {
                scannerTokenList.getRestOfScannerToken();
                continue;
            } else if (500 < scannerTokenList.getCurrentScannerToken().getCode()) {
                res.add(statement.getLabelStatement(scannerTokenList));
            } else if (isIdentifier(scannerTokenList.getCurrentScannerToken().getCode())) {
                res.add(statement.getProcedureCallStatement(scannerTokenList.getRestOfScannerToken()));
            } else {
                System.out.println(scannerTokenList.getToken());
                throw new ParserException(ErrorMessages.UNEXPECTED_TOKEN);
            }
            scannerTokenList.getRestOfScannerToken();
        }
        return res;
    }

}
