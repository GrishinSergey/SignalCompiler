package scanner;

import resources.tables.scannertables.ConstTable;
import resources.tables.scannertables.DelimitersTable;
import resources.tables.scannertables.IdentifiersTable;
import resources.tables.scannertables.KeyWordsTable;
import resources.token.ScannerToken;

import java.util.List;

public class ScannerList {

    private List<ScannerToken> scannerTokenList;
    private ScannerToken previous;

    ScannerList(List<ScannerToken> scannerTokenList) {
        this.scannerTokenList = scannerTokenList;
    }

    public ScannerToken getCurrentScannerToken() {
        return scannerTokenList.get(0);
    }

    public ScannerList getRestOfScannerToken() {
        previous = scannerTokenList.get(0);
        scannerTokenList.remove(0);
        return this;
    }

    public ScannerToken getPrevious() {
        return previous;
    }

    public boolean isNotEnded() {
        return scannerTokenList.size() > 0;
    }

    public String getToken(int tokenCode) {
        return getTokenString(tokenCode);
    }

    public String getToken() {
        return getTokenString(scannerTokenList.get(0).getCode());
    }

    private String getTokenString(int tokenCode) {
        if (tokenCode < 13) {
            return DelimitersTable.getInstance().getToken(tokenCode);
        }
        if (tokenCode < 200) {
            return KeyWordsTable.getInstance().getToken(tokenCode);
        }
        if (tokenCode < 500) {
            return IdentifiersTable.getInstance().getToken(tokenCode);
        }
        return ConstTable.getInstance().getToken(tokenCode);
    }

}
