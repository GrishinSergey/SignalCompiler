package resources.tables.parsertables;

public class LabelsParserTable extends AbstractParserTable {

    private static LabelsParserTable instance;

    public static LabelsParserTable getInstance() {
        if (instance == null) {
            instance = new LabelsParserTable();
        }
        return instance;
    }

    private LabelsParserTable() {
        super();
    }

}
