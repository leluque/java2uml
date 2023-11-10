package br.com.luque.java2uml.reflection;

import java.util.Objects;

public class Parameter extends BaseItem {
    private Clazz type;

    public Parameter(String name, Clazz type, ClazzPool clazzPool) {
        super(name, clazzPool);
        setType(type);
    }

    public Clazz getType() {
        return type;
    }

    public void setType(Clazz type) {
        this.type = Objects.requireNonNull(type);
    }
}
