package semantic;

import exceptions.SemanticException;
import generator.tokens.PortToken;
import parser.rules.*;
import resources.ErrorMessages;
import resources.tables.generatortables.PortGeneratorTable;
import resources.tables.parsertables.ConstParserTable;
import resources.tables.parsertables.LabelsParserTable;
import resources.tables.parsertables.ProceduresParserTable;
import resources.tables.parsertables.VariablesParserTable;
import resources.token.GeneratorToken;
import resources.token.ParserToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SemanticAnalysis {

    private List<ParserToken> syntaxTree;
    private List<String> declarations = new ArrayList<>();

    public SemanticAnalysis(List<ParserToken> syntaxTree) {
        this.syntaxTree = syntaxTree;
    }

    public SemanticAnalysis runSemanticAnalysis() throws SemanticException {
        addProgramIdentifierToDeclarations(syntaxTree.get(0));
        isCorrectDeclarations(((Block.BlockToken) syntaxTree.get(1)).getDeclarations());
        isCorrectStatements(((Block.BlockToken) syntaxTree.get(1)).getStatements());
        return this;
    }

    public SemanticAnalysis runDataFlowAnalysis() {
        return this;
    }

    private void addProgramIdentifierToDeclarations(ParserToken token) {
        if (token.getToken().equals("programDeclaration")) {
            declarations.add(((Program.ProgramToken) token).getName());
        } else {
            declarations.add(((Program.ProcedureToken) token).getName());
        }
    }

    private void isCorrectDeclarations(DeclarationsList.DeclarationsListToken declarations) throws SemanticException {
        if (declarations.getLabels() != null) {
            for (ParserToken token: declarations.getLabels()) {
                String name = ((LabelDeclarations.LabelToken) token).getName();
                addNewIdentifierOrThrowNewException(name,
                        name + ErrorMessages.DECLARATION_ERROR, token.getLine());
            }
        }
        if (declarations.getConsts() != null) {
            for (ParserToken token: declarations.getConsts()) {
                String label = ((ConstDeclarations.ConstDeclarationToken) token).getValue();
                if (LabelsParserTable.getInstance().findToken(label) != null) {
                    throwExceptionWithMessage("value " + label + " already defined as label", token.getLine());
                }
                String name = ((ConstDeclarations.ConstDeclarationToken) token).getName();
                addNewIdentifierOrThrowNewException(name,
                        name + ErrorMessages.DECLARATION_ERROR, token.getLine());
            }
        }
        if (declarations.getVariables() != null) {
            for (ParserToken token: declarations.getVariables()) {
                for (ParserToken token1: ((VariableDeclarations.VariablesDeclarationToken) token).getIdentifiers()) {
                    String name = ((IdentifiersList.IdentifierToken) token1).getName();
                    addNewIdentifierOrThrowNewException(name,
                            name + ErrorMessages.DECLARATION_ERROR, token.getLine());
                }
            }
        }
        if (declarations.getFunctions() != null) {
            for (ParserToken token: declarations.getFunctions()) {
                String name = ((FunctionDeclarations.FunctionToken) token).getName();
                addNewIdentifierOrThrowNewException(name,
                        name + ErrorMessages.DECLARATION_ERROR, token.getLine());
            }
        }
        if (declarations.getProcedures() != null) {
            for (ParserToken token: declarations.getProcedures()) {
                String name = ((Program.ProcedureToken) token).getName();
                addNewIdentifierOrThrowNewException(name,
                        name + ErrorMessages.DECLARATION_ERROR, token.getLine());
            }
        }
    }

    private void isCorrectStatements(List<ParserToken> statements) throws SemanticException {
        for (ParserToken token: statements) {
            switch (token.getToken()) {
                case "loop":
                    isCorrectStatements(((SingleStatement.LoopStatementToken) token).getStatements());
                    break;
                case "goto":
                    isCorrectGoto(token);
                    break;
                case "link":
                    isCorrectLinkOfPortAndVariable(token);
                    break;
                case "in":
                    isCorrectInPort(token);
                    break;
                case "out":
                    isCorrectOutPort(token);
                    break;
                case "label":
                    isCorrectLabel(token);
                    break;
                case "procedurecall":
                    isCorrectProcedureCall(token);
            }
        }
    }

    private void isCorrectGoto(ParserToken token) throws SemanticException {
        String label = ((SingleStatement.GotoStatementToken) token).getLabelIdentifier();
        if (LabelsParserTable.getInstance().findToken(label) == null) {
            throwExceptionWithMessage("label " + label + " is undefined", token.getLine());
        }
    }

    private void isCorrectLinkOfPortAndVariable(ParserToken token) throws SemanticException {
        String varName = ((SingleStatement.LinkStatementToken) token).getVariable();
        String portName = ((SingleStatement.LinkStatementToken) token).getValue();
        ParserToken variable = VariablesParserTable.getInstance().findToken(varName);
        if (variable == null) {
            throwExceptionWithMessage("variable " + varName + " is undefined", token.getLine());
        }
        PortGeneratorTable.getInstance().add(new PortToken(token.getLine(), varName, portName));
    }

    private void isCorrectLabel(ParserToken token) throws SemanticException {
        String label;
        label = ((SingleStatement.LabelStatementToken) token).getLabelIdentifier();
        if (LabelsParserTable.getInstance().findToken(label) == null) {
            throwExceptionWithMessage("label " + label + " is undefined", token.getLine());
        }
        List<ParserToken> list = new ArrayList<>();
        list.add(((SingleStatement.LabelStatementToken) token).getStatement());
        isCorrectStatements(list);
    }

    private void isCorrectOutPort(ParserToken token) throws SemanticException {
        String label;
        String portName;
        GeneratorToken port;
        label = ((SingleStatement.OutStatementToken) token).getOutSteam();
        if (LabelsParserTable.getInstance().findToken(label) != null) {
            throwExceptionWithMessage(label + " is defined as label", token.getLine());
        }
        portName = ((SingleStatement.OutStatementToken) token).getOutSteam();
        port = PortGeneratorTable.getInstance().findToken(portName);
        if (port == null) {
            throwExceptionWithMessage(portName + " is not defined yet", token.getLine());
        }
        if (((PortToken) port).getPortType() != null) {
            throwExceptionWithMessage(portName + "'s type is already seted", token.getLine());
        }
        ((PortToken) port).setPortOutType();
    }

    private void isCorrectInPort(ParserToken token) throws SemanticException {
        String label;
        String portName;
        label = ((SingleStatement.InStatementToken) token).getInSteam();
        if (LabelsParserTable.getInstance().findToken(label) != null) {
            throwExceptionWithMessage(label + " is defined as label", token.getLine());
        }
        portName = ((SingleStatement.InStatementToken) token).getInSteam();
        GeneratorToken port = PortGeneratorTable.getInstance().findToken(portName);
        if (port == null) {
            throwExceptionWithMessage(portName + " is not defined yet", token.getLine());
        }
        if (((PortToken) port).getPortType() != null) {
            throwExceptionWithMessage(portName + "'s type is already seted", token.getLine());
        }
        ((PortToken) port).setPortInType();
    }

    private void isCorrectProcedureCall(ParserToken token) throws SemanticException {
        String name = ((SingleStatement.ProcedureCallStatementToken) token).getProcedureName();
        Program.ProcedureToken procedureToken =
                (Program.ProcedureToken) ProceduresParserTable.getInstance().findToken(name);
        if (procedureToken == null) {
            throwExceptionWithMessage(name + ErrorMessages.UNDECLARED_PROCEDURE, token.getLine());
        }
        if (((SingleStatement.ProcedureCallStatementToken) token).getParameters().size() >
                procedureToken.getParams().size()) {
            throwExceptionWithMessage("count of fact arguments is more than actual", token.getLine());
        } else if (((SingleStatement.ProcedureCallStatementToken) token).getParameters().size() <
                procedureToken.getParams().size()) {
            throwExceptionWithMessage("count of actual arguments is more than fact", token.getLine());
        } else {
            List<ParserToken> factParams = ((SingleStatement.ProcedureCallStatementToken) token).getParameters();
            List<String> formalParams = procedureToken.getParams();
            for (int i = 0; i < factParams.size(); i++) {
                ParserToken variable = VariablesParserTable
                        .getInstance()
                        .findToken(((IdentifiersList.IdentifierToken) factParams.get(i)).getName());
                ParserToken constant = ConstParserTable
                        .getInstance()
                        .findToken(((IdentifiersList.IdentifierToken) factParams.get(i)).getName());
                if (constant != null) {
                    if (!Objects.equals(((ConstDeclarations.ConstDeclarationToken) constant).getAttribute(), formalParams.get(i))) {
                        throwExceptionWithMessage("facts argument's type is not " + formalParams.get(i), token.getLine());
                    }
                } else if (variable != null) {
                    if (!Objects.equals(((VariableDeclarations.VariableToken) variable).getAttribute(), formalParams.get(i))) {
                        throwExceptionWithMessage("facts argument's type is not " + formalParams.get(i), token.getLine());
                    }
                } else {
                    throwExceptionWithMessage("fact argument is not defined", token.getLine());
                }
            }
        }
    }

    private void throwExceptionWithMessage(String message, int line) throws SemanticException {
        SemanticException e = new SemanticException(message);
        e.setLine(line);
        throw e;
    }

    private void addNewIdentifierOrThrowNewException(String identifier, String exceptionMessage, int line) throws SemanticException {
        for (String id : declarations) {
            if (id.equals(identifier)) {
                throwExceptionWithMessage(exceptionMessage, line);
            }
        }
        declarations.add(identifier);
    }

    public List<ParserToken> getTree() {
        return syntaxTree;
    }
}
