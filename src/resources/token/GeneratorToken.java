package resources.token;

public class GeneratorToken {

    protected String token;
    protected int lineNumber;

    public GeneratorToken(String token, int lineNumber) {
        this.token = token;
        this.lineNumber = lineNumber;
    }

    public String getToken() {
        return token;
    }

    public int getLineNumber() {
        return lineNumber;
    }

}
