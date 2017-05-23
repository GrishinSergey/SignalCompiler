package parser.rules;

import exceptions.ParserException;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.util.ArrayList;
import java.util.List;

public class VariableDeclarations extends AbstractRule {

    List<ParserToken> getVariablesDeclarations(ScannerList scannerTokenList) throws ParserException {
        List<ParserToken> res = new ArrayList<>();
        while (scannerTokenList.isNotEnded() &&
                111 != scannerTokenList.getCurrentScannerToken().getCode() &&
                101 != scannerTokenList.getCurrentScannerToken().getCode() &&
                102 != scannerTokenList.getCurrentScannerToken().getCode()) {

            throwExceptionIfUnexpectedIdentifier(scannerTokenList.getCurrentScannerToken().getCode());
            int lineNumber = scannerTokenList.getCurrentScannerToken().getLineNumber();
            List<ParserToken> identifiers = new IdentifiersList().getIdentifiersList(scannerTokenList, true);
            if (6 != scannerTokenList.getCurrentScannerToken().getCode()) {
                throw new ParserException("Unexpected symbol in variable declaration. Expected : ");
            }
            scannerTokenList.getRestOfScannerToken();
            if (!isAttribute(scannerTokenList.getCurrentScannerToken().getCode())) {
                throw new ParserException("Unexpected attribute. ");
            }
            String attribute = scannerTokenList.getToken();
            scannerTokenList.getRestOfScannerToken();
            throwExceptionIfUnexpectedEndOfLine(scannerTokenList.getCurrentScannerToken().getCode());
            res.add(new VariableDeclarationToken("variableDeclaration", lineNumber, attribute, identifiers));
            scannerTokenList.getRestOfScannerToken();
        }
        return res;
    }

    public class VariableDeclarationToken extends ParserToken {

        private String type;
        private List<ParserToken> identifiers;

        VariableDeclarationToken(String token, int line, String type, List<ParserToken> identifiers) {
            super(token, line);
            this.type = type;
            this.identifiers = identifiers;
        }

        public String getType() {
            return type;
        }

        public List<ParserToken> getIdentifiers() {
            return identifiers;
        }
    }

}
