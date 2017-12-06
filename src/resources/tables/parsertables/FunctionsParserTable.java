package resources.tables.parsertables;

public class FunctionsParserTable extends AbstractParserTable {

    private static FunctionsParserTable instance;

    public static FunctionsParserTable getInstance() {
        if (instance == null) {
            instance = new FunctionsParserTable();
        }
        return instance;
    }

    private FunctionsParserTable() {
        super();
    }

}
