package br.com.luque.java2uml.reflection;

import java.util.Objects;

public abstract class BaseItem implements Item {
    private ClazzPool clazzPool;
    private String name;

    public BaseItem(String name, ClazzPool clazzPool) {
        setName(name);
        setClazzPool(clazzPool);
    }

    @Override
    public String getName() {
        return name;
    }

    private void setName(String name) {
        Objects.requireNonNull(name);
        name = name.trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Cannot be empty!");
        }
        this.name = name;
    }

    @Override
    public ClazzPool getClazzPool() {
        return clazzPool;
    }

    private void setClazzPool(ClazzPool clazzPool) {
        this.clazzPool = Objects.requireNonNull(clazzPool);
    }
}
