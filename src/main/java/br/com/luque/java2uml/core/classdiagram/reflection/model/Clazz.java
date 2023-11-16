package br.com.luque.java2uml.core.classdiagram.reflection.model;

import java.lang.reflect.Modifier;
import java.util.Objects;

public abstract class Clazz extends BaseItem {
    private final Class<?> javaClass;
    private final boolean abstract_;
    private boolean interface_;

    public Clazz(Class<?> javaClass, ClazzPool clazzPool) {
        super(Objects.requireNonNull(javaClass).getSimpleName(), clazzPool);
        this.javaClass = javaClass;
        this.abstract_ = Modifier.isAbstract(javaClass.getModifiers());
        this.interface_ = javaClass.isInterface();
    }

    public Class<?> getJavaClass() {
        return javaClass;
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
