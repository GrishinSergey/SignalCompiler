package resources.token;

public class ScannerToken {

    private int code;
    private int lineNumber;

    public ScannerToken(int code, int lineNumber) {
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
