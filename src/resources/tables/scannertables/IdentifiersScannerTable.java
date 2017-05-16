package resources.tables.scannertables;

public class IdentifiersScannerTable extends AbstractScannerTable {

    private static IdentifiersScannerTable instance;
    private int tokenCode;

    public static IdentifiersScannerTable getInstance() {
        if (instance == null) {
            instance = new IdentifiersScannerTable();
        }
        return instance;
    }

    private IdentifiersScannerTable() {
        super();
        tokenCode = 200;
    }

    public int add(String token) {
        table.put(++tokenCode, token);
        return tokenCode;
    }

}
