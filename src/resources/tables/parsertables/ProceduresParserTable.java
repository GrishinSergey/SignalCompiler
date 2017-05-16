package resources.tables.parsertables;

public class ProceduresParserTable extends AbstractParserTable {

    private static ProceduresParserTable instance;

    public static ProceduresParserTable getInstance() {
        if (instance == null) {
            instance = new ProceduresParserTable();
        }
        return instance;
    }

    private ProceduresParserTable() {
        super();
    }

}
