package parser.rules;

import exceptions.ParserException;
import resources.ErrorMessages;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.util.ArrayList;
import java.util.List;

public class StatementsList extends AbstractRule {

    private List<ParserToken> res = new ArrayList<>();
    private SingleStatement statement = new SingleStatement();

    List<ParserToken> getStatementsList(ScannerList scannerTokenList) throws ParserException {
        ParserToken parserToken;
        while (scannerTokenList.isNotEnded() &&
                108 != scannerTokenList.getCurrentScannerToken().getCode() &&
                116 != scannerTokenList.getCurrentScannerToken().getCode()) {
            parserToken = getStatement(scannerTokenList.getCurrentScannerToken().getCode(), scannerTokenList);
            scannerTokenList.getRestOfScannerToken();
            if (null == parserToken) {
                continue;
            }
            res.add(parserToken);
        }
        return res;
    }

    ParserToken getStatement(int code, ScannerList scannerTokenList) throws ParserException {
          if (114 == code) {
            return statement.getLinkStatement(scannerTokenList.getRestOfScannerToken());
        } if (117 == code) {
            return statement.getGotoStatement(scannerTokenList.getRestOfScannerToken());
        } if (115 == code) {
            return statement.getLoopStatement(scannerTokenList.getRestOfScannerToken());
        } if (118 == code) {
            return statement.getReturnStatement(scannerTokenList.getRestOfScannerToken());
        } if (8 == code) {
            return statement.getAssemblyInsertStatement(scannerTokenList.getRestOfScannerToken());
        } if (112 == code) {
            return statement.getInStatement(scannerTokenList.getRestOfScannerToken());
        } if (113 == code) {
            return statement.getOutStatement(scannerTokenList.getRestOfScannerToken());
        } if (5 == code) {
            return null;
        } if (500 < code) {
            return statement.getLabelStatement(scannerTokenList);
        } if (isIdentifier(code)) {
            return statement.getProcedureCallStatement(scannerTokenList.getRestOfScannerToken());
        }
        throw new ParserException(ErrorMessages.UNEXPECTED_TOKEN);
    }

}