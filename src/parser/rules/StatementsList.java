package parser.rules;

import exceptions.ParserException;
import resources.ErrorMessages;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.util.ArrayList;
import java.util.List;

class StatementsList {

    List<ParserToken> getStatementsList(ScannerList scannerTokenList) throws ParserException {
        List<ParserToken> res = new ArrayList<>();
        while (scannerTokenList.isNotEnded() &&
                108 != scannerTokenList.getCurrentScannerToken().getCode() &&
                116 != scannerTokenList.getCurrentScannerToken().getCode()) {
            switch (scannerTokenList.getCurrentScannerToken().getCode()) {
                case 114:
                    res.add(new SingleStatement().getLinkStatement(scannerTokenList.getRestOfScannerToken()));
                    break;
                case 117:
                    res.add(new SingleStatement().getGotoStatement(scannerTokenList.getRestOfScannerToken()));
                    break;
                case 115:
                    res.add(new SingleStatement().getLoopStatement(scannerTokenList.getRestOfScannerToken()));
                    break;
                case 118:
                    res.add(new SingleStatement().getReturnStatement(scannerTokenList.getRestOfScannerToken()));
                    break;
                case 8:
                    res.add(new SingleStatement().getAssemblyInsertStatement(scannerTokenList.getRestOfScannerToken()));
                    break;
                case 112:
                    res.add(new SingleStatement().getInStatement(scannerTokenList.getRestOfScannerToken()));
                    break;
                case 113:
                    res.add(new SingleStatement().getOutStatement(scannerTokenList.getRestOfScannerToken()));
                    break;
                case 5:
                    break;
                default:
                    throw new ParserException(ErrorMessages.UNEXPECTED_TOKEN);
            }
            scannerTokenList.getRestOfScannerToken();
        }
        return res;
    }

}
