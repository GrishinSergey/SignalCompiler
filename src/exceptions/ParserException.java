package exceptions;

public class ParserException extends exceptions.Exception {

    public ParserException() {
        super("");
    }

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, int line) {
        super(message);
        setLine(line);
    }

}
