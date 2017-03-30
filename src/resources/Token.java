package resources;

public class Token {

    private int code;
    private int lineNumber;

    public Token(int code, int lineNumber) {
        this.code = code;
        this.lineNumber = lineNumber;
    }

    public int getCode() {
        return code;
    }

    public int getLineNumber() {
        return lineNumber;
    }

}
