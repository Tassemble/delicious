package org.tassemble.base.commons.dao.sql;

import java.util.HashMap;

public class AddParamMap extends HashMap<String, Object> {

    private static final long serialVersionUID = 5736555920514900959L;

    public boolean containsKey(Object key) {
        key = ((String) key).toLowerCase();
        return super.containsKey(key);
    }

    public Object get(Object key) {
        key = ((String) key).toLowerCase();
        return super.get(key);
    }

    public Object put(String key, Object value) {
        key = key.toLowerCase();
        return super.put(key, value);
    }

    public Object remove(Object key) {
        key = ((String) key).toLowerCase();
        return super.remove(key);
    }
}
