package resources.tables.parsertables;

import resources.tables.Table;

public class LabelsTable extends Table {

    private static LabelsTable instance;
    private int index;

    public static LabelsTable getInstance() {
        if (instance == null) {
            instance = new LabelsTable();
        }
        return instance;
    }

    private LabelsTable() {
        super();
        index = 1;
    }

    public int add(String token) {
        table.put(++index, token);
        return index;
    }

}
