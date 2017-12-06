package exceptions;

public class SemanticException extends exceptions.Exception {

    public SemanticException() {
        super("");
    }

    public SemanticException(String message) {
        super(message);
    }

}
