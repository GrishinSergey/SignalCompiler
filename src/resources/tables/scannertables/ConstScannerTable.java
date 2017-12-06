package resources.tables.scannertables;

public class ConstScannerTable extends AbstractScannerTable {

    private static ConstScannerTable instance;
    private int tokenCode;

    public static ConstScannerTable getInstance() {
        if (instance == null) {
            instance = new ConstScannerTable();
        }
        return instance;
    }

    private ConstScannerTable() {
        super();
        tokenCode = 500;
    }

    public int add(String token) {
        table.put(++tokenCode, token);
        return tokenCode;
    }

}