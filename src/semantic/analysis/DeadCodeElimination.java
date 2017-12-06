package semantic.analysis;

import resources.token.ParserToken;

import java.util.ArrayList;
import java.util.List;

public class DeadCodeElimination {

    private List<String> usedVariables;
    private List<String> usedConstants;
    private List<String> usedFunctions;
    private List<String> usedProcedures;
    private List<String> usedLabels;

    private List<ParserToken> syntaxTree;

    public DeadCodeElimination() {
        usedVariables = new ArrayList<>();
        usedConstants = new ArrayList<>();
        usedFunctions = new ArrayList<>();
        usedProcedures = new ArrayList<>();
        usedLabels = new ArrayList<>();
        syntaxTree = new ArrayList<>();
    }

    public DeadCodeElimination runDeadCodeElimination() {

        return this;
    }

    private void traverseStatementsDeleteDeadCodeAndSetUsedDefinitions(List<ParserToken> statements) {

    }

    private void traverseDeclarationsAndDeleteUnused(List<ParserToken> declarations) {

    }

}
