package com.birkagal.keyvaluestore;

public class KeyValuePairBoundary {
    private String key;
    private Object value;

    public KeyValuePairBoundary() {
    }

    public KeyValuePairBoundary(String key, Object value) {
        super();
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "KeyValuePairBoundary [key=" + key + ", value=" + value + "]";
    }
}
