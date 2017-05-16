package resources.tables.scannertables;

public class DelimitersScannerTable extends AbstractScannerTable {

    private static DelimitersScannerTable instance;

    public static DelimitersScannerTable getInstance() {
        if (instance == null) {
            instance = new DelimitersScannerTable();
        }
        return instance;
    }

    private DelimitersScannerTable() {
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
