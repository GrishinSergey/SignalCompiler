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
        table.put(104, "SIGNAL");
        table.put(105, "COMPLEX");
        table.put(105, "INTEGER");
        table.put(106, "FLOAT");
        table.put(107, "BLOCKFLOAT");
        table.put(108, "EXT");
        table.put(109, "END");
    }

}