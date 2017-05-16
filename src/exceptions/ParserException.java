package exceptions;

public class ParserException extends Exception {

    private int lineNumber = -1;

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, int lineNumber) {
        super(message);
        this.lineNumber = lineNumber;
    }

    public ParserException() {}


    public int getLineNumber() {
        return lineNumber;
    }

}
