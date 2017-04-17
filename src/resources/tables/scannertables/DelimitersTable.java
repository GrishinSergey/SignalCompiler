package resources.tables.scannertables;

import resources.tables.Table;

public class DelimitersTable extends Table {

    private static DelimitersTable instance;

    public static DelimitersTable getInstance() {
        if (instance == null) {
            instance = new DelimitersTable();
        }
        return instance;
    }

    private DelimitersTable() {
        super();
        table.put(1, "(");
        table.put(2, ")");
        table.put(3, "(*");
        table.put(4, "*)");
        table.put(5, ";");
        table.put(6, ":");
        table.put(7, ",");
        table.put(8, "($");
        table.put(9, "$)");
        table.put(10, ".");
        table.put(11, "\\");
        table.put(12, "=");
    }

}
