package parser.rules;

import exceptions.ParserException;
import resources.ErrorMessages;
import resources.token.ParserToken;
import scanner.ScannerList;

import java.util.List;

public class DeclarationsList extends AbstractRule {

    private DeclarationsListToken declarationsListToken;

    DeclarationsListToken getDeclarationsList(ScannerList scannerTokenList) throws ParserException {
        declarationsListToken = new DeclarationsListToken(
                "declarationsList",
                scannerTokenList.getCurrentScannerToken().getLine()
        );
        while (scannerTokenList.isNotEnded() && 102 != scannerTokenList.getCurrentScannerToken().getCode()) {
            getDeclaration(scannerTokenList);
        }
        return declarationsListToken;
    }

    private void getDeclaration(ScannerList scannerTokenList) throws ParserException {
        if (109 == scannerTokenList.getCurrentScannerToken().getCode()) {
            declarationsListToken.setLabels(
                    new LabelDeclarations().getLabelsDeclarations(scannerTokenList.getRestOfScannerToken()));
        } else if (103 == scannerTokenList.getCurrentScannerToken().getCode()) {
            declarationsListToken.setVariables(
                    new VariableDeclarations().getVariablesDeclarations(scannerTokenList.getRestOfScannerToken()));
        } else if (101 == scannerTokenList.getCurrentScannerToken().getCode()) {
            declarationsListToken.setProcedures(
                    new ProcedureDeclarations().getProceduresDeclarations(scannerTokenList,"procedureDef"));
        } else if (111 == scannerTokenList.getCurrentScannerToken().getCode()) {
            declarationsListToken.setFunctions(
                    new FunctionDeclarations().getFunctionDeclarations(scannerTokenList.getRestOfScannerToken()));
        } else if (119 == scannerTokenList.getCurrentScannerToken().getCode()) {
            declarationsListToken.setConstants(
                    new ConstDeclarations().getConstDeclarations(scannerTokenList.getRestOfScannerToken()));
        } else {
            throw new ParserException(ErrorMessages.UNEXPECTED_DECLARATION);
        }
    }

    public class DeclarationsListToken extends ParserToken {

        private List<ParserToken> labels;
        private List<ParserToken> constants;
        private List<ParserToken> variables;
        private List<ParserToken> functions;
        private List<ParserToken> procedures;

        DeclarationsListToken(String token, int line) {
            super(token, line);
        }

        void setLabels(List<ParserToken> labels) {
            this.labels = labels;
        }

        void setConstants(List<ParserToken> constants) {
            this.constants = constants;
        }

        void setVariables(List<ParserToken> variables) {
            this.variables = variables;
        }

        void setFunctions(List<ParserToken> functions) {
            this.functions = functions;
        }

        void setProcedures(List<ParserToken> procedures) {
            this.procedures = procedures;
        }

        public List<ParserToken> getLabels() {
            return labels;
        }

        public List<ParserToken> getConsts() {
            return constants;
        }

        public List<ParserToken> getVariables() {
            return variables;
        }

        public List<ParserToken> getFunctions() {
            return functions;
        }

        public List<ParserToken> getProcedures() {
            return procedures;
        }

    }

}
