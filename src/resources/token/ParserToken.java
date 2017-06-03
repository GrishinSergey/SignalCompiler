package resources.token;

public class ParserToken {

    protected String token;
    protected int line;

    public ParserToken(String token, int line) {
        this.token = token;
        this.line = line;
    }

    public String getToken() {
        return token;
    }

    public int getLine() {
        return line;
    }

}
