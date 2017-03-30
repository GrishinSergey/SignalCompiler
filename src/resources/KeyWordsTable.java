package resources;

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
        table.put(105, "INTEGER");
        table.put(106, "FLOAT");
        table.put(107, "BLOCKFLOAT");
        table.put(109, "END");
        table.put(109, "LABEL");
        table.put(109, "PROGRAM");
        table.put(109, "DEFFUNC");
        table.put(109, "IN");
        table.put(109, "OUT");
        table.put(109, "LINK");
        table.put(109, "LOOP");
        table.put(109, "ENDLOOP");
        table.put(109, "GOTO");
    }

}