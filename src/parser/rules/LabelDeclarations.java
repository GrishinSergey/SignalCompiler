package parser.rules;

import exceptions.ParserException;
import resources.ErrorMessages;
import resources.tables.parsertables.LabelsParserTable;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.util.ArrayList;
import java.util.List;

public class LabelDeclarations extends AbstractRule {

    List<ParserToken> getLabelsDeclarations(ScannerList scannerTokenList) throws ParserException {
        throwExceptionIfUnexpectedUnsignedInteger(scannerTokenList.getCurrentScannerToken().getCode());
        List<ParserToken> res = new ArrayList<>();
        boolean flag = false;
        while (scannerTokenList.isNotEnded() &&
                (500 < scannerTokenList.getCurrentScannerToken().getCode() ||
                 7 == scannerTokenList.getCurrentScannerToken().getCode())) {
            if (7 == scannerTokenList.getCurrentScannerToken().getCode()) {
                if (!flag) {
                    throw new ParserException(ErrorMessages.UNEXPECTED_LABEL_DECLARATION);
                }
                flag = false;
            } else if (500 < scannerTokenList.getCurrentScannerToken().getCode()) {
                LabelToken lt = new LabelToken("label",
                        scannerTokenList.getCurrentScannerToken().getLine(),
                        scannerTokenList.getToken());
                res.add(lt);
                LabelsParserTable.getInstance().add(lt);
                if (flag) {
                    throw new ParserException(ErrorMessages.UNEXPECTED_LABEL_DECLARATION);
                }
                flag = true;
            } else {
                throw new ParserException(ErrorMessages.UNEXPECTED_LABEL_DECLARATION);
            }
            scannerTokenList.getRestOfScannerToken();
        }
        scannerTokenList.getRestOfScannerToken();
//        throwExceptionIfUnexpectedEndOfLine(
//                scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode());
        return res;
    }

    public class LabelToken extends ParserToken {

        private String name;

        LabelToken(String token, int line, String name) {
            super(token, line);
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
