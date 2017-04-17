package resources.tables.scannertables;

import resources.tables.Table;

public class ConstTable extends Table {

    private static ConstTable instance;
    private int tokenCode;

    public static ConstTable getInstance() {
        if (instance == null) {
            instance = new ConstTable();
        }
        return instance;
    }

    private ConstTable() {
        super();
        tokenCode = 500;
    }

    public int add(String token) {
        table.put(++tokenCode, token);
        return tokenCode;
    }

}