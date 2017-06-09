package resources.tables;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTable<T> {

    protected HashMap<Integer, T> table;
    protected int index;

    public AbstractTable() {
        this.table = new HashMap<>();
        index = 0;
    }

    public T getToken(int tokenCode) {
        return table.get(tokenCode);
    }

    public T findToken(String token) {
        for (Map.Entry t: table.entrySet()) {
            if (token.equals(t.getValue().toString())) {
                return (T) t.getValue();
            }
        }
        return null;
    }

}
