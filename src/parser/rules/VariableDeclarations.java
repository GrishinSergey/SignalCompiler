package parser.rules;

import exceptions.ParserException;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.util.List;

class VariableDeclarations extends AbstractRule {

    List<ParserToken> getVariablesDeclarations(ScannerList scannerTokenList) throws ParserException {


        return null;
    }

    private class VariableToken extends ParserToken {

        private String name;
        private String type;

        public VariableToken(String token, int line, String name, String type) {
            super(token, line);
            this.name = name;
            this.type = type;
        }

    }

}
