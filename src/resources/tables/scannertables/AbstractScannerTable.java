package resources.tables.scannertables;

import resources.tables.AbstractTable;

import java.util.Map;

abstract class AbstractScannerTable extends AbstractTable<String> {

    AbstractScannerTable() {
        super();
    }

    public int getTokenCode(String token) {
        for (Map.Entry t: table.entrySet()) {
            if (token.equals(t.getValue().toString())) {
                return (int) t.getKey();
            }
        }
        return -1;
    }

}