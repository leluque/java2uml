package br.com.luque.java2uml.reflection.model;

import br.com.luque.java2uml.ClazzPool;

import java.lang.reflect.Modifier;
import java.util.Objects;

public abstract class Clazz extends BaseItem {
    private final Class<?> originalClass;
    private final boolean abstract_;
    private boolean interface_;

    public Clazz(Class<?> originalClass, ClazzPool clazzPool) {
        super(originalClass.getSimpleName(), clazzPool);
        this.originalClass = Objects.requireNonNull(originalClass);
        this.abstract_ = Modifier.isAbstract(getOriginalClass().getModifiers());
    }

    public Class<?> getOriginalClass() {
        return originalClass;
    }

    public boolean isAbstract() {
        return abstract_;
    }

    public boolean isInterface() {
        return interface_;
    }

    public void setInterface(boolean interface_) {
        this.interface_ = interface_;
    }

    public void extractClassInfo() {
    }
}
