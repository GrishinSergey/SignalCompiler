package resources.tables;

import java.util.HashMap;

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

}
