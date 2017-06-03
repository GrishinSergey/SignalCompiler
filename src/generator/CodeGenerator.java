package generator;

import exceptions.GeneratorException;
import resources.token.GeneratorToken;
import resources.token.ParserToken;

import java.util.List;

public class CodeGenerator {

    private List<ParserToken> syntaxTree;
    private SemanticAnalysis analysis;

    public CodeGenerator(List<ParserToken> syntaxTree) {
        this.syntaxTree = syntaxTree;
        analysis = new SemanticAnalysis(syntaxTree);
    }

    public CodeGenerator runGenerator() throws GeneratorException {
        analysis.runSemanticAnalysis();
        return this;
    }

    public class LinkedPortGeneratorToken extends GeneratorToken {

        private String linkedVariable;
        private String portName;

        public LinkedPortGeneratorToken(String token, int lineNumber, String linkedVariable, String portName) {
            super(token, lineNumber);
            this.linkedVariable = linkedVariable;
            this.portName = portName;
        }

        public String getLinkedVariable() {
            return linkedVariable;
        }

        public String getPortName() {
            return portName;
        }

    }

}
