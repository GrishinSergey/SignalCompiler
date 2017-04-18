package resources.tables.parsertables;

import resources.tables.Table;

public class FunctionsTable extends Table {

    private static FunctionsTable instance;
    private int index;

    public static FunctionsTable getInstance() {
        if (instance == null) {
            instance = new FunctionsTable();
        }
        return instance;
    }

    private FunctionsTable() {
        super();
        index = 1;
    }

    public int add(String token) {
        table.put(++index, token);
        return index;
    }


}
