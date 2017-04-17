package resources.tables.scannertables;

import resources.tables.Table;

public class KeyWordsTable extends Table {

    private static KeyWordsTable instance;

    public static KeyWordsTable getInstance() {
        if (instance == null) {
            instance = new KeyWordsTable();
        }
        return instance;
    }

    private KeyWordsTable() {
        super();
        table.put(101, "PROCEDURE");
        table.put(102, "BEGIN");
        table.put(103, "VAR");
        table.put(104, "SIN");
        table.put(105, "INTEGER");
        table.put(106, "FLOAT");
        table.put(107, "BLOCKFLOAT");
        table.put(108, "END");
        table.put(109, "LABEL");
        table.put(110, "PROGRAM");
        table.put(111, "DEFFUNC");
        table.put(112, "IN");
        table.put(113, "OUT");
        table.put(114, "LINK");
        table.put(115, "LOOP");
        table.put(116, "ENDLOOP");
        table.put(117, "GOTO");
        table.put(118, "RETURN");
    }

}