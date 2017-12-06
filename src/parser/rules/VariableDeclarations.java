package parser.rules;

import exceptions.ParserException;
import resources.ErrorMessages;
import resources.tables.parsertables.VariablesParserTable;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.util.ArrayList;
import java.util.List;

public class VariableDeclarations extends AbstractRule {

    List<ParserToken> getVariablesDeclarations(ScannerList scannerTokenList) throws ParserException {
        List<ParserToken> res = new ArrayList<>();
        while (scannerTokenList.isNotEnded() &&
                101 != scannerTokenList.getCurrentScannerToken().getCode() &&
                102 != scannerTokenList.getCurrentScannerToken().getCode() &&
                111 != scannerTokenList.getCurrentScannerToken().getCode()) {
            throwExceptionIfUnexpectedIdentifier(scannerTokenList.getCurrentScannerToken().getCode());
            int lineNumber = scannerTokenList.getCurrentScannerToken().getLine();
            List<ParserToken> identifiers = new IdentifiersList().getIdentifiersList(scannerTokenList);
            if (6 != scannerTokenList.getCurrentScannerToken().getCode()) {
                throw new ParserException(ErrorMessages.UNEXPECTED_SYMBOL_IN_VAR_DECLARATION);
            }
            scannerTokenList.getRestOfScannerToken();
            if (!isAttribute(scannerTokenList.getCurrentScannerToken().getCode())) {
                throw new ParserException("Unexpected attribute. ");
            }
            String attribute = scannerTokenList.getToken();
            scannerTokenList.getRestOfScannerToken();
            throwExceptionIfUnexpectedEndOfLine(scannerTokenList.getCurrentScannerToken().getCode());
            res.add(new VariablesDeclarationToken("variableDeclaration", lineNumber, attribute, identifiers));
            fillVariableParserTable(attribute, identifiers);
            scannerTokenList.getRestOfScannerToken();
        }
        return res;
    }

    private void fillVariableParserTable(String attribute, List<ParserToken> identifiers) {
        IdentifiersList.IdentifierToken id;
        for (ParserToken token: identifiers) {
            id = ((IdentifiersList.IdentifierToken) token);
            VariablesParserTable.getInstance().add(
                    new VariableToken(
                            "variableDeclaration",
                            id.getLine(),
                            attribute,
                            id.getName())
            );
        }
    }

    public class VariableToken extends ParserToken {

        private String attribute;
        private String name;

        VariableToken(String token, int line, String attribute, String name) {
            super(token, line);
            this.attribute = attribute;
            this.name = name;
        }

        public String getAttribute() {
            return attribute;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public class VariablesDeclarationToken extends ParserToken {

        private String type;
        private List<ParserToken> identifiers;

        VariablesDeclarationToken(String token, int line, String type, List<ParserToken> identifiers) {
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
