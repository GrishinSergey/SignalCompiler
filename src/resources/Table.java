package resources;

import java.util.HashMap;
import java.util.Map;

abstract class Table {

    HashMap<Integer, String> table;

    Table() {
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
