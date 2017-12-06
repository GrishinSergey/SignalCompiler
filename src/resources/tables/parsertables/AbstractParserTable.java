package resources.tables.parsertables;

import resources.tables.AbstractTable;
import resources.token.ParserToken;

import java.util.Map;

abstract class AbstractParserTable extends AbstractTable<ParserToken> {

    AbstractParserTable() {
        super();
    }

    public int add(ParserToken token) {
        table.put(++index, token);
        return index;
    }

    public int countDeclarationsWithName(String name) {
        int counter = 0;
        for (Map.Entry t: table.entrySet()) {
            if (name.equals(t.getValue().toString())) {
                counter++;
            }
        }
        return counter;
    }

}