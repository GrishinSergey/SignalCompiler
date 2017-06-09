package exceptions;

abstract public class Exception extends java.lang.Exception {

    private int line;
    private String fileName;
    private String message;

    Exception(String message) {
        super(message);
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLine() {
        return line;
    }

    public exceptions.Exception getException() {
        StackTraceElement[] ste = {new StackTraceElement("", "", fileName, line)};
        this.setStackTrace(ste);
        return this;
    }

}