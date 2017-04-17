package resources.tables.scannertables;

import resources.tables.Table;

public class IdentifiersTable extends Table {

    private static IdentifiersTable instance;
    private int tokenCode;

    public static IdentifiersTable getInstance() {
        if (instance == null) {
            instance = new IdentifiersTable();
        }
        return instance;
    }

    private IdentifiersTable() {
        super();
        tokenCode = 200;
    }

    public int add(String token) {
        table.put(++tokenCode, token);
        return tokenCode;
    }

}
