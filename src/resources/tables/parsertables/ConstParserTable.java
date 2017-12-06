package resources.tables.parsertables;

public class ConstParserTable extends AbstractParserTable {

    private static ConstParserTable instance;

    public static ConstParserTable getInstance() {
        if (instance == null) {
            instance = new ConstParserTable();
        }
        return instance;
    }

    private ConstParserTable() {
        super();
    }

}
