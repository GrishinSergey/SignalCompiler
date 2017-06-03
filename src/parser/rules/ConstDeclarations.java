package parser.rules;

import exceptions.ParserException;
import resources.tables.parsertables.ConstParserTable;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.util.ArrayList;
import java.util.List;

public class ConstDeclarations extends AbstractRule {

    List<ParserToken> getConstDeclarations(ScannerList scannerTokenList) throws ParserException {
        List<ParserToken> res = new ArrayList<>();
        while (scannerTokenList.isNotEnded() &&
                111 != scannerTokenList.getCurrentScannerToken().getCode() &&
                101 != scannerTokenList.getCurrentScannerToken().getCode() &&
                102 != scannerTokenList.getCurrentScannerToken().getCode() &&
                103 != scannerTokenList.getCurrentScannerToken().getCode()) {
            throwExceptionIfUnexpectedIdentifier(scannerTokenList.getCurrentScannerToken().getCode());
            int lineNumber = scannerTokenList.getCurrentScannerToken().getLineNumber();
            String constName = scannerTokenList.getToken();
            if (12 != scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode()) {
                throw new ParserException("Error const declaration");
            }
            String constValue = scannerTokenList.getRestOfScannerToken().getToken();
            scannerTokenList.getRestOfScannerToken();
            throwExceptionIfUnexpectedEndOfLine(scannerTokenList.getCurrentScannerToken().getCode());

            ConstDeclarationToken constDeclarationToken = new ConstDeclarationToken(
                    "constDeclaration",
                    lineNumber,
                    constName,
                    constValue
            );
            ConstParserTable.getInstance().add(constDeclarationToken);
            res.add(constDeclarationToken);
            scannerTokenList.getRestOfScannerToken();

        }
        return res;
    }

    public class ConstDeclarationToken extends ParserToken {

        private String constName;
        private String value;

        ConstDeclarationToken(String token, int line, String constName, String value) {
            super(token, line);
            this.constName = constName;
            this.value = value;
        }

        public String getName() {
            return constName;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return constName;
        }
    }

}
