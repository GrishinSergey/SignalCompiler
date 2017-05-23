package parser.rules;

import exceptions.ParserException;
import resources.ErrorMessages;
import resources.tables.parsertables.VariablesParserTable;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.util.ArrayList;
import java.util.List;

public class IdentifiersList extends AbstractRule {

    List<ParserToken> getIdentifiersList(ScannerList scannerTokenList, boolean addToTableFlag) throws ParserException {
        throwExceptionIfUnexpectedIdentifier(scannerTokenList.getCurrentScannerToken().getCode());
        List<ParserToken> res = new ArrayList<>();
        boolean flag = false;
        while (scannerTokenList.isNotEnded() &&
                (7 == scannerTokenList.getCurrentScannerToken().getCode() ||
                isIdentifier(scannerTokenList.getCurrentScannerToken().getCode()))) {
            if (7 == scannerTokenList.getCurrentScannerToken().getCode()) {
                if (!flag) {
                    throw new ParserException();
                }
                flag = false;
            } else if (isIdentifier(scannerTokenList.getCurrentScannerToken().getCode())) {
                IdentifierToken it = new IdentifierToken("variable",
                        scannerTokenList.getCurrentScannerToken().getLineNumber(),
                        scannerTokenList.getToken());
                res.add(it);
                if (addToTableFlag) {
                    VariablesParserTable.getInstance().add(it);
                }
                if (flag) {
                    throw new ParserException(ErrorMessages.UNEXPECTED_VARIABLES_LIST);
                }
                flag = true;
            } else {
                throw new ParserException(ErrorMessages.UNEXPECTED_VARIABLES_LIST);
            }
            scannerTokenList.getRestOfScannerToken();
        }
        return res;
    }

    public class IdentifierToken extends ParserToken {

        private String name;

        IdentifierToken(String token, int line, String name) {
            super(token, line);
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
