package resources.tables.parsertables;

public class VariablesParserTable extends AbstractParserTable {

    private static VariablesParserTable instance;

    public static VariablesParserTable getInstance() {
        if (instance == null) {
            instance = new VariablesParserTable();
        }
        return instance;
    }

    private VariablesParserTable() {
        super();
    }

}
