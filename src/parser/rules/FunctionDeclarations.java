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
            int lineNumber = scannerTokenList.getCurrentScannerToken().getLineNumber();
            if (12 != scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode()) {
                throw new ParserException("expected assume ");
            }
            if (104 != scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode()) {
                throw new ParserException("expected SIN ");
            }
            String expression = scannerTokenList.getToken();
            if (11 != scannerTokenList.getRestOfScannerToken().getCurrentScannerToken().getCode()) {
                throw new ParserException("expected \\ ");
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
        private int countValues;
        private int step;
        private String expression;

        FunctionToken(String token, int line, String name, int countValues, int step, String expression) {
            super(token, line);
            this.name = name;
            this.countValues = countValues;
            this.step = step;
            this.expression = expression;
        }

        public String getName() {
            return name;
        }

        public int getCountValues() {
            return countValues;
        }

        public int getStep() {
            return step;
        }

        public String getExpression() {
            return expression;
        }

        public double[] calculateFunctionValues() {
            double [] d = new double[countValues];
            for (int i = 0, j = 0; i < countValues; i += step, j++) {
                d[j] = Math.sin(i);
            }
            return d;
        }

    }

}