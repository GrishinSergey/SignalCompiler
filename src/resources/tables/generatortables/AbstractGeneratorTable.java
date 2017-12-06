package resources.tables.generatortables;


import resources.tables.AbstractTable;
import resources.token.GeneratorToken;


public class AbstractGeneratorTable  extends AbstractTable<GeneratorToken> {

    AbstractGeneratorTable() {
        super();
    }

    public int add(GeneratorToken token) {
        table.put(++index, token);
        return index;
    }

}
