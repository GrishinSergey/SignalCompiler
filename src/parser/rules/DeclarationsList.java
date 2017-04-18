package parser.rules;

import exceptions.ParserException;
import resources.ErrorMessages;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.util.ArrayList;
import java.util.List;

class DeclarationsList extends AbstractRule {

    List<List<ParserToken>> getDeclarationsList(ScannerList scannerTokenList) throws ParserException {
        List<List<ParserToken>> res = new ArrayList<>();
        while (scannerTokenList.isNotEnded() && 102 != scannerTokenList.getCurrentScannerToken().getCode()) {
            System.out.println(scannerTokenList.getToken());
            res.add(getDeclaration(scannerTokenList));
        }
        return res;
    }

    private List<ParserToken> getDeclaration(ScannerList scannerTokenList) throws ParserException {
        if (109 == scannerTokenList.getCurrentScannerToken().getCode()) {
            return new LabelDeclarations().getLabelsDeclarations(scannerTokenList.getRestOfScannerToken());
        } if (103 == scannerTokenList.getCurrentScannerToken().getCode()) {
            return null;
        } if (101 == scannerTokenList.getCurrentScannerToken().getCode()) {
            return null;
        } if (111 == scannerTokenList.getCurrentScannerToken().getCode()) {
            return new FunctionDeclarations().getFunctionDeclarations(scannerTokenList.getRestOfScannerToken());
        }
        throw new ParserException(ErrorMessages.UNEXPECTED_DECLARATION);
    }

}
