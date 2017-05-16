package parser.rules;

import exceptions.ParserException;
import resources.tables.parsertables.LabelsParserTable;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.util.ArrayList;
import java.util.List;

class LabelDeclarations extends AbstractRule {

    List<ParserToken> getLabelsDeclarations(ScannerList scannerTokenList) throws ParserException {
        throwExceptionIfUnexpectedUnsignedInteger(scannerTokenList.getCurrentScannerToken().getCode());
        List<ParserToken> res = new ArrayList<>();
        boolean flag = false;
        while (scannerTokenList.isNotEnded() &&
                (500 < scannerTokenList.getCurrentScannerToken().getCode() ||
                 7 == scannerTokenList.getCurrentScannerToken().getCode())) {
            if (7 == scannerTokenList.getCurrentScannerToken().getCode()) {
                if (!flag) {
                    throw new ParserException("error in labels declaration ");
                }
                flag = false;
            } else if (500 < scannerTokenList.getCurrentScannerToken().getCode()) {
                LabelToken lt = new LabelToken("label",
                        scannerTokenList.getCurrentScannerToken().getLineNumber(),
                        scannerTokenList.getToken());
                res.add(lt);
                LabelsParserTable.getInstance().add(lt);
                if (flag) {
                    throw new ParserException("error in labels declaration ");
                }
                flag = true;
            } else {
                throw new ParserException("error in labels declaration ");
            }
            scannerTokenList.getRestOfScannerToken();
        }
        scannerTokenList.getRestOfScannerToken();
//        throwExceptionIfUnexpectedEndOfLine(
//                scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode());
        return res;
    }

    private class LabelToken extends ParserToken {

        private String labelIdentifier;

        LabelToken(String token, int line, String labelIdentifier) {
            super(token, line);
            this.labelIdentifier = labelIdentifier;
        }

    }

}
