package resources.asm.templates;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class Assembler {

    public String getProcedureDefinitionAssembler(String procedureName) {
        return  procedureName +
                " proc\npush bp\nmov bp, sp\n\n" +
                ";; here some proc code\n\n" +
                "pop di\npop si\npop bx\n" +
                "mov sp, bp\npop bp\nret 0\nendp\n\n";
    }

    public String getFunctionDefinitionAssembler(String name, List<String> valuesArray) {
        return name + " word ptr " + String.join(", ", valuesArray) + "\n";
    }

    public String getProcedureDefinitionAssembler(String name, String definitions, String statements) {
        return name + " proc\n" + definitions +
                "push bp\nmov bp, sp\n\n" +
                statements + "\nendp\n";
    }

    public String getProgramDefinitionAssembler(String definitions, String statements) {
        return "data segment\n" +
                definitions +
                "data ends\ncode segment\nassume ds:data cs:code\n@_start_:\n" +
                statements +
                "\ncode ends\n_start_";
    }

    public String getReturn() {
        return "ret\n";
    }

    public String getGotoAssembler(String label) {
        return "jmp " + label + "\n";
    }

    public String getLabelStatementAssembler(String label, String statementAssemblerCode) {
        return label + ":\n" + statementAssemblerCode + "\n";
    }

    public String getLoopAssembler(String inputCode, int index) {
        String label = "@_loop_" + index;
        return label + ":\n" + inputCode +
                "jmp " + label + "\n";
    }

    public String getAssemblyInsertCode(String pathToFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathToFile));
            StringBuilder code = new StringBuilder();
            for (Object s: br.lines().toArray()) {
                code.append((String) s).append("\n");
            }
            return code.toString();
        } catch (FileNotFoundException e) {
            return "";
        }
    }

    public String getLinkStreamAndVariableAssembler(String variable, String stream) {
        return "mov ax, " + stream + "\nmov " + variable + ", ax\n";
    }

    public String getProcedureCallAssembler(String procedureName, List<String> paramNames) {
        StringBuilder code = new StringBuilder();
        for (String param: paramNames) {
            code.append("push ").append(param).append("\n");
        }
        code.append("call ").append(procedureName).append("\n");
        return code.toString();
    }

    public String getConstantAssembler(String name, String value) {
        return name + " equ " + value + "\n";
    }

    public String getVariableAssembler(String name, String type) {
        StringBuilder res = new StringBuilder(name);
        switch (type) {
            case "INTEGER":
            case "FLOAT":
                res.append(" dw 0\n");
                break;
            case "BLOCKFLOAT":
                res.append(" word ptr 0\n");
                break;
        }
        return res.toString();
    }

}
