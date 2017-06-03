package generator;

import exceptions.GeneratorException;
import parser.rules.*;
import resources.ErrorMessages;
import resources.tables.parsertables.*;
import resources.token.ParserToken;

import java.util.List;

class SemanticAnalysis {

    private List<ParserToken> syntaxTree;

    SemanticAnalysis(List<ParserToken> syntaxTree) {
        this.syntaxTree = syntaxTree;
    }

    SemanticAnalysis runSemanticAnalysis() throws GeneratorException {
        isCorrectDeclarations(((Block.BlockToken) syntaxTree.get(1)).getDeclarations());
        return this;
    }

    private boolean isCorrectDeclarations(DeclarationsList.DeclarationsListToken declarations) throws GeneratorException {
        if (declarations.getLabels() != null) {
            for (ParserToken token: declarations.getLabels()) {
                String name = ((LabelDeclarations.LabelToken) token).getName();
                throwExceptionIfRepeatedIdentifier(countDeclarationsWithName(name),
                        name + ErrorMessages.DECLARATION_ERROR + token.getLine());
            }
        }
        if (declarations.getConsts() != null) {
            for (ParserToken token: declarations.getConsts()) {
                String name = ((ConstDeclarations.ConstDeclarationToken) token).getName();
                throwExceptionIfRepeatedIdentifier(countDeclarationsWithName(name),
                        name + ErrorMessages.DECLARATION_ERROR + token.getLine());
            }
        }
        if (declarations.getVariables() != null) {
            for (ParserToken token: declarations.getVariables()) {
                for (ParserToken token1: ((VariableDeclarations.VariablesDeclarationToken) token).getIdentifiers()) {
                    String name = ((IdentifiersList.IdentifierToken) token1).getName();
                    throwExceptionIfRepeatedIdentifier(countDeclarationsWithName(name),
                            name + ErrorMessages.DECLARATION_ERROR + token1.getLine());
                }
            }
        }
        if (declarations.getFunctions() != null) {
            for (ParserToken token: declarations.getFunctions()) {
                String name = ((FunctionDeclarations.FunctionToken) token).getName();
                throwExceptionIfRepeatedIdentifier(countDeclarationsWithName(name),
                        name + ErrorMessages.DECLARATION_ERROR + token.getLine());
            }
        }
        if (declarations.getProcedures() != null) {
            for (ParserToken token: declarations.getProcedures()) {
                String name = ((Program.ProcedureToken) token).getName();
                throwExceptionIfRepeatedIdentifier(countDeclarationsWithName(name),
                        name + ErrorMessages.DECLARATION_ERROR + token.getLine());
            }
        }
        return false;
    }

    private boolean isCorrectStatements() {
        return false;
    }

    private boolean isCorrectName() {
        return false;
    }

    private boolean isCorrectLinkedPort() {
        return false;
    }

    private boolean isDefinedLabel() {
        return false;
    }

    private boolean isDefinedVariable() {
        return false;
    }

    private boolean isDefinedProcedure() {
        return false;
    }

    private boolean isDefinedConstName() {
        return false;
    }

    private boolean isDefinedFunction() {
        return false;
    }

    private int countDeclarationsWithName(String name) {
        int counter = 0;
        counter += LabelsParserTable.getInstance().countDeclarationsWithName(name);
        counter += ConstParserTable.getInstance().countDeclarationsWithName(name);
        counter += VariablesParserTable.getInstance().countDeclarationsWithName(name);
        counter += FunctionsParserTable.getInstance().countDeclarationsWithName(name);
        counter += ProceduresParserTable.getInstance().countDeclarationsWithName(name);
        return counter;
    }

    private void throwExceptionIfRepeatedIdentifier(int count, String message) throws GeneratorException {
        if (1 < count) {
            throw new GeneratorException(message);
        }
    }

}
