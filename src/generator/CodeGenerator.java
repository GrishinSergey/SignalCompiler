package generator;

import parser.rules.*;
import resources.asm.templates.Assembler;
import resources.token.ParserToken;

import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {

    private List<ParserToken> syntaxTree;
    private Assembler assembler;
    private String result;

    public CodeGenerator(List<ParserToken> syntaxTree) {
        this.syntaxTree = syntaxTree;
        assembler = new Assembler();
    }

    public CodeGenerator runGenerator() {
        if (syntaxTree.get(0).getToken().equals("programDeclaration")) {
            result = assembler.getProgramDefinitionAssembler(
                    generateDefinitions(((Block.BlockToken) syntaxTree.get(1)).getDeclarations()),
                    generateStatements(((Block.BlockToken) syntaxTree.get(1)).getStatements(), new StringBuilder())
            );
        } else {
            result = assembler.getProcedureDefinitionAssembler(
                    ((Program.ProcedureToken) syntaxTree.get(0)).getName(),
                    generateDefinitions(((Block.BlockToken) syntaxTree.get(1)).getDeclarations()),
                    generateStatements(((Block.BlockToken) syntaxTree.get(1)).getStatements(), new StringBuilder())
            );
        }
        return this;
    }

    public String getResult() {
        return result;
    }

    private String generateStatements(List<ParserToken> statements, StringBuilder res) {
        for (ParserToken token: statements) {
            switch (token.getToken()) {
                case "asm":
                    res.append(assembler.getAssemblyInsertCode(((SingleStatement.AssemblyInsertStatementToken) token).getPathToAsmFile()));
                    break;
                case "loop":
                    res.append(Loop.generateLoop(token, assembler, this));
                    break;
                case "goto":
                    res.append(assembler.getGotoAssembler(((SingleStatement.GotoStatementToken) token).getLabelIdentifier()));
                    break;
                case "return":
                    res.append(assembler.getReturn());
                    break;
                case "link":
                    res.append(assembler.getLinkStreamAndVariableAssembler(
                            ((SingleStatement.LinkStatementToken) token).getVariable(),
                            ((SingleStatement.LinkStatementToken) token).getValue()
                    ));
                    break;
                case "label":
                    List<ParserToken> list = new ArrayList<>();
                    list.add(((SingleStatement.LabelStatementToken) token).getStatement());
                    res.append(assembler.getLabelStatementAssembler(
                            ((SingleStatement.LabelStatementToken) token).getLabelIdentifier(),
                            generateStatements(list, new StringBuilder())
                     ));
                    break;
                case "procedurecall":
                    List<String> params = new ArrayList<>();
                    for (ParserToken token1: ((SingleStatement.ProcedureCallStatementToken) token).getParameters()) {
                        params.add(((IdentifiersList.IdentifierToken) token1).getName());
                    }
                    res.append(assembler.getProcedureCallAssembler(
                            ((SingleStatement.ProcedureCallStatementToken) token).getProcedureName(),
                            params
                    ));
            }
        }
        return res.toString();
    }

    private String generateDefinitions(DeclarationsList.DeclarationsListToken declarations) {
        StringBuilder definitions = new StringBuilder();
        if (declarations.getConsts() != null) {
            for (ParserToken token: declarations.getConsts()) {
                String name = ((ConstDeclarations.ConstDeclarationToken) token).getName();
                String value = ((ConstDeclarations.ConstDeclarationToken) token).getValue();
                definitions.append(assembler.getConstantAssembler(name, value));
            }
            definitions.append("\n");
        }
        if (declarations.getVariables() != null) {
            for (ParserToken token: declarations.getVariables()) {
                for (ParserToken token1: ((VariableDeclarations.VariablesDeclarationToken) token).getIdentifiers()) {
                    String name = ((IdentifiersList.IdentifierToken) token1).getName();
                    String type = ((VariableDeclarations.VariablesDeclarationToken) token).getType();
                    definitions.append(assembler.getVariableAssembler(name, type));
                }
            }
            definitions.append("\n");
        }
        if (declarations.getFunctions() != null) {
            for (ParserToken token: declarations.getFunctions()) {
                String name = ((FunctionDeclarations.FunctionToken) token).getName();
                definitions.append(assembler.getFunctionDefinitionAssembler(
                        name,
                        ((FunctionDeclarations.FunctionToken) token).getValues()));
            }
            definitions.append("\n");
        }
        if (declarations.getProcedures() != null) {
            for (ParserToken token: declarations.getProcedures()) {
                String name = ((Program.ProcedureToken) token).getName();
                definitions.append(assembler.getProcedureDefinitionAssembler(name));
            }
        }
        return definitions.toString();
    }

    private static class Loop {

        private static int index;

        private static String generateLoop(ParserToken token, Assembler assembler, CodeGenerator generator) {
            return assembler.getLoopAssembler(
                    generator.generateStatements(
                            ((SingleStatement.LoopStatementToken) token).getStatements(),
                            new StringBuilder()),
                    ++index);
        }

    }

}
