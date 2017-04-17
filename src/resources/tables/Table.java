package resources.tables;

import java.util.HashMap;
import java.util.Map;

abstract public class Table {

    protected HashMap<Integer, String> table;

    protected Table() {
        table = new HashMap<>();
    }

    public int getTokenCode(String token) {
        for (Map.Entry lex : table.entrySet()) {
            if (token.equals(lex.getValue().toString())) {
                return Integer.valueOf(lex.getKey().toString());
            }
        }
        return -1;
    }

    public String getToken(int tokenCode) {
        return table.get(tokenCode);
    }

}