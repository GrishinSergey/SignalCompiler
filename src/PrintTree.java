//import parser.rules.*;
//import resources.token.ParserToken;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//class PrintTree {
//
//    void print(List<ParserToken> tree) {
//        System.out.println("<SignalProgram>");
//        System.out.println("    <Program>");
//        for (ParserToken token: tree) {
//            if (Objects.equals(token.getToken(), "programDeclaration")) {
//                printProgram((Program.ProgramToken) token);
//            } else if (Objects.equals(token.getToken(), "procedureDeclaration")) {
//                System.out.println("        <ProgramDeclaration>");
//                printProcedure((Program.ProcedureToken)token, "            ");
//            }
//            if (Objects.equals(token.getToken(), "block")) {
//                printBlock((Block.BlockToken) token, "        ");
//            }
//        }
//    }
//
//    private void printBlock(Block.BlockToken token, String shift) {
//        System.out.println(shift + "<Block>");
//        if (token.getDeclarations() != null) {
//            System.out.println(shift + "    <Declarations>");
//            for (List<ParserToken> tokens: token.getDeclarations()) {
//                printDeclarations(tokens, shift + "    ");
//            }
//        }
//        if (token.getStatements() != null) {
//            System.out.println(shift + "    <Statements>");
//            printStatements(shift + "        ", token.getStatements());
//        }
//    }
//
//    private void printStatements(String shift, List<ParserToken> tokens) {
//        for (ParserToken token: tokens) {
//            if (Objects.equals(token.getToken(), "asm")) {
//                System.out.println(shift + "<AssemblyStatement>");
//                System.out.println(shift + "    ($");
//                System.out.println(shift + "    <AsmInsertFilePath>");
//                System.out.println(shift + "        " + ((SingleStatement.AssemblyInsertStatementToken) token).getPathToAsmFile());
//                System.out.println(shift + "    $)");
//            } else if (Objects.equals(token.getToken(), "loop")) {
//                System.out.println(shift + "<LoopStatement>");
//                System.out.println(shift + "LOOP");
//                printStatements(shift + "    ", ((SingleStatement.LoopStatementToken) token).getStatements());
//                System.out.println(shift + "ENDLOOP");
//                System.out.println(shift + ";");
//            } else if (Objects.equals(token.getToken(), "goto")) {
//                System.out.println(shift + "<GotoStatement>");
//                System.out.println(shift + "    GOTO");
//                System.out.println(shift + "    <UnsignedInteger>");
//                System.out.println(shift + "        " + ((SingleStatement.GotoStatementToken) token).getName());
//                System.out.println(shift + "    ;");
//            } else if (Objects.equals(token.getToken(), "return")) {
//                System.out.println(shift + "<ReturnStatement>");
//                System.out.println(shift + "    RETURN");
//                System.out.println(shift + "    ;");
//            } else if (Objects.equals(token.getToken(), "link")) {
//                System.out.println(shift + "<LinkStatement>");
//                System.out.println(shift + "    LINK");
//                System.out.println(shift + "    <Identifier>");
//                System.out.println(shift + "        " + ((SingleStatement.LinkStatementToken) token).getVariable());
//                System.out.println(shift + "    ,");
//                System.out.println(shift + "    <UnsignedInteger>");
//                System.out.println(shift + "        " + ((SingleStatement.LinkStatementToken) token).getValue());
//                System.out.println(shift + "    ;");
//            } else if (Objects.equals(token.getToken(), "in")) {
//                System.out.println(shift + "<InStatement>");
//                System.out.println(shift + "    IN");
//                System.out.println(shift + "    <UnsignedInteger>");
//                System.out.println(shift + "        " + ((SingleStatement.InStatementToken) token).getInSteam());
//                System.out.println(shift + "    ;");
//            } else if (Objects.equals(token.getToken(), "out")) {
//                System.out.println(shift + "<OutStatement>");
//                System.out.println(shift + "    OUT");
//                System.out.println(shift + "    <UnsignedInteger>");
//                System.out.println(shift + "        " + ((SingleStatement.OutStatementToken) token).getOutSteam());
//                System.out.println(shift + "    ;");
//            } else if (Objects.equals(token.getToken(), "label")) {
//                System.out.println(shift + "<LabelStatement>");
//                System.out.println(shift + "    <UnsignedInteger>");
//                System.out.println(shift + "    " + ((SingleStatement.LabelStatementToken) token).getName());
//                System.out.println(shift + "    :");
//                List<ParserToken> statement = new ArrayList<>();
//                statement.add(((SingleStatement.LabelStatementToken) token).getStatement());
//                printStatements(shift+ "    ", statement);
//                System.out.println(shift + "    ;");
//            } else if (Objects.equals(token.getToken(), "procedurecall")) {
//                System.out.println(shift + "<ProcedureCallStatement>");
//                System.out.println(shift + "    <Identifier>");
//                System.out.println(shift + "        " + ((SingleStatement.ProcedureCallStatementToken) token).getProcedureName());
//                if (((SingleStatement.ProcedureCallStatementToken) token).getParameters() != null) {
//                    System.out.println(shift + "    <ActualArguments>");
//                    System.out.println(shift + "        (");
//                    int i = 1;
//                    for (ParserToken paramToken: ((SingleStatement.ProcedureCallStatementToken) token).getParameters()) {
//                        System.out.println(shift + "        <Identifier>");
//                        System.out.println(shift + "            " + ((IdentifiersList.IdentifierToken) paramToken).getName());
//                        if (i < ((SingleStatement.ProcedureCallStatementToken) token).getParameters().size()) {
//                            System.out.println(shift + "        ,");
//                        }
//                        i++;
//                    }
//                    System.out.println(shift + "        )");
//                }
//                System.out.println(shift + "    ;");
//            }
//        }
//    }
//
//    private void printDeclarations(List<ParserToken> tokens, String shift) {
//        if (tokens.get(0).getToken().equals("label")) {
//            System.out.println(shift + "        <LabelDeclaration>");
//            System.out.println(shift + "            LABEL");
//            System.out.println(shift + "            <LabelsList>");
//            for (ParserToken dtoken : tokens) {
//                System.out.println(shift + "                <UnsignedInteger>");
//                System.out.println(shift + "                    " + ((LabelDeclarations.LabelToken) dtoken).getName());
//                System.out.println(shift + "                ,");
//            }
//            System.out.println(shift + "            ;");
//
//        } else if (tokens.get(0).getToken().equals("constDeclaration")) {
//            System.out.println(shift + "        <ConstDeclaration>");
//            int i = 0;
//            System.out.println(shift + "            CONST");
//            for (ParserToken dtoken : tokens) {
//                System.out.println(shift + "                <Identifier>");
//                System.out.println(shift + "                     " + ((ConstDeclarations.ConstDeclarationToken) dtoken).getName());
//                System.out.println(shift + "                =");
//                System.out.println(shift + "                <UnsignedInteger>");
//                System.out.println(shift + "                    " + ((ConstDeclarations.ConstDeclarationToken) dtoken).getValue());
//                if (i < tokens.size()) {
//                    System.out.println("                        ,");
//                }
//                i++;
//            }
//            System.out.println("                    ;");
//        }else if (tokens.get(0).getToken().equals("variableDeclaration")) {
//            System.out.println(shift + "        <VariableDeclaration>");
//            System.out.println(shift + "            VAR");
//            System.out.println(shift + "            <DeclarationsList>");
//            for (ParserToken dtoken: tokens) {
//                System.out.println(shift + "                <Declaration>");
//                System.out.println(shift + "                    <Attribute>");
//                System.out.println(shift + "                        " + ((VariableDeclarations.VariableDeclarationToken) dtoken).getType());
//                System.out.println(shift + "                    <IdentifiersList>");
//                int i = 1;
//                for (ParserToken vartokens: ((VariableDeclarations.VariableDeclarationToken) dtoken).getIdentifiers()) {
//                    System.out.println(shift + "                        <Identifier>");
//                    System.out.println(shift + "                            " + ((IdentifiersList.IdentifierToken) vartokens).getName());
//                    if (i < ((VariableDeclarations.VariableDeclarationToken) dtoken).getIdentifiers().size()) {
//                        System.out.println(shift + "                        ,");
//                    }
//                    i++;
//                }
//                System.out.println("                    ;");
//
//            }
//        } else if (tokens.get(0).getToken().equals("function")) {
//            System.out.println(shift + "        <MathFunctionDeclaration>");
//            System.out.println(shift + "            <FunctionsList>");
//            for (ParserToken ftoken: tokens) {
//                System.out.println(shift + "                <Function>");
//                System.out.println(shift + "                    <Identifier>");
//                System.out.println(shift + "                        " + ((FunctionDeclarations.FunctionToken) ftoken).getName());
//                System.out.println(shift + "                    <Expression>");
//                System.out.println(shift + "                        " + ((FunctionDeclarations.FunctionToken) ftoken).getExpression());
//                System.out.println(shift + "                    <FunctionCharacteristic>");
//                System.out.println(shift + "                        \\");
//                System.out.println(shift + "                        <UnsignedInteger>");
//                System.out.println(shift + "                            " + ((FunctionDeclarations.FunctionToken) ftoken).getCountValues());
//                System.out.println(shift + "                        ,");
//                System.out.println(shift + "                        <UnsignedInteger>");
//                System.out.println(shift + "                            " + ((FunctionDeclarations.FunctionToken) ftoken).getStep());
//                System.out.println(shift + "                    ;");
//            }
//        } else if (tokens.get(0).getToken().equals("procedureDef")) {
//            System.out.println(shift + "        <ProcedureDeclaration>");
//            for (ParserToken ptoken: tokens) {
//                printProcedure((Program.ProcedureToken) ptoken, shift + "            ");
//            }
//        }
//    }
//
//    private void printProgram(Program.ProgramToken token) {
//        System.out.println("<ProgramDeclaration>");
//        System.out.println("    <Identifier>");
//        System.out.println("        " + token.getName() + "\n    ;");
//    }
//
//    private void printProcedure(Program.ProcedureToken token, String shift) {
//        System.out.println(shift + "<Procedure>");
//        System.out.println(shift + "    <Identifier>");
//        System.out.println(shift + "        " + token.getName());
//
//        if (token.getParams() != null) {
//            System.out.println(shift + "    <ParametersList>");
//            int i = 1;
//            for (String param: token.getParams()) {
//                System.out.println(shift + "        <Attribute>");
//                System.out.println(shift + "            " + param);
//                if (i < token.getParams().size()) {
//                    System.out.println(shift + "        ,");
//                }
//                i++;
//            }
//        }
//        System.out.println(shift + "    ;");
//    }
//
//}
