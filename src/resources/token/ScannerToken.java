package resources.token;

public class ScannerToken {

    private int code;
    private int line;

    public ScannerToken(int code, int lineNumber) {
        this.code = code;
        this.line = lineNumber;
    }

    public int getCode() {
        return code;
    }

    public int getLine() {
        return line;
    }

}
