package resources.tables.generatortables;


public class PortGeneratorTable extends AbstractGeneratorTable {

    private static PortGeneratorTable instance;

    public static PortGeneratorTable getInstance() {
        if (instance == null) {
            instance = new PortGeneratorTable();
        }
        return instance;
    }

    private PortGeneratorTable() {
        super();
    }

}
