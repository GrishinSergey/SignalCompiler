package resources.tables.parsertables;

import resources.token.ParserToken;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractParserTable {

    private HashMap<Integer, ParserToken> table;
    private int index;

    AbstractParserTable() {
        this.table = new HashMap<>();
        index = 0;
    }

    public int getTokenCode(String token) {
        for (Map.Entry lex : table.entrySet()) {
            if (token.equals(lex.getValue().toString())) {
                return Integer.valueOf(lex.getKey().toString());
            }
        }
        return -1;
    }

    public int add(ParserToken token) {
        table.put(++index, token);
        return index;
    }

    public ParserToken getToken(int tokenCode) {
        return table.get(tokenCode);
    }

}