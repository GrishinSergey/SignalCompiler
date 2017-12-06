package parser.rules;

import exceptions.ParserException;
import resources.ErrorMessages;
import resources.tables.parsertables.FunctionsParserTable;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.util.ArrayList;
import java.util.List;


public class FunctionDeclarations  extends AbstractRule {

    List<ParserToken> getFunctionDeclarations(ScannerList scannerTokenList) throws ParserException {
        List<ParserToken> res = new ArrayList<>();
        while (scannerTokenList.isNotEnded() &&
                101 != scannerTokenList.getCurrentScannerToken().getCode() &&
                102 != scannerTokenList.getCurrentScannerToken().getCode()) {
            throwExceptionIfUnexpectedIdentifier(scannerTokenList.getCurrentScannerToken().getCode());
            String functionName = scannerTokenList.getToken();
            int lineNumber = scannerTokenList.getCurrentScannerToken().getLine();
            if (12 != scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode()) {
                throw new ParserException(ErrorMessages.UNEXPECTED_ASSUMING_OF_FUNCTION_BODY);
            }
            if (104 != scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode()) {
                throw new ParserException(ErrorMessages.UNEXPECTED_EXPRESSION_OF_FUNCTION);
            }
            String expression = scannerTokenList.getToken();
            if (11 != scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode()) {
                throw new ParserException(ErrorMessages.UNEXPECTED_FUNCTIONS_ATTRIBUTE);
            }
            throwExceptionIfUnexpectedUnsignedInteger(
                    scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode());
            int countValues = Integer.parseInt(scannerTokenList.getToken());
            if (7 != scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode()) {
                throw new ParserException(ErrorMessages.UNEXPECTED_DELIMITER);
            }
            throwExceptionIfUnexpectedUnsignedInteger(
                    scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode());
            int step = Integer.parseInt(scannerTokenList.getToken());
            throwExceptionIfUnexpectedEndOfLine(
                    scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode());
            FunctionToken ft = new FunctionToken("function", lineNumber, functionName, countValues, step, expression);
            res.add(ft);
            FunctionsParserTable.getInstance().add(ft);
            scannerTokenList.getRestOfScannerToken();
        }
        return res;
    }

    public class FunctionToken extends ParserToken {

        private String name;
        private int maxIntervalValue;
        private int countValues;
        private String expression;
        private List<String> values;

        FunctionToken(String token, int line, String name, int maxIntervalValue, int countValues, String expression) {
            super(token, line);
            this.name = name;
            this.maxIntervalValue = maxIntervalValue;
            this.countValues = countValues;
            this.expression = expression;
            this.values = new ArrayList<>();
            calculateFunctionValues();
        }

        public String getName() {
            return name;
        }

        public List<String> getValues() {
            return values;
        }

        private void calculateFunctionValues() {
            double step = (maxIntervalValue * 1.0) / countValues;
            for (double i = 1.0; i <= maxIntervalValue; i+= step) {
                values.add(String.valueOf(Math.sin(i)));
            }
        }

    }

}