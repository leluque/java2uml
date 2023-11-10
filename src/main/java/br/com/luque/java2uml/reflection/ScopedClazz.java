package br.com.luque.java2uml.reflection;

import java.util.stream.Stream;

public class ScopedClazz extends Clazz {
    private Clazz superclass;
    private Clazz[] interfaces;
    private Field[] fields;
    private Method[] methods;

    public ScopedClazz(Class<?> originalClass, ClazzPool clazzPool) {
        super(originalClass, clazzPool);
        extractClassInfo();
    }

    public static ScopedClazz newInterface(Class<?> originalClass, ClazzPool clazzPool) {
        ScopedClazz clazz = new ScopedClazz(originalClass, clazzPool);
        clazz.setInterface(true);
        return clazz;
    }

    private void extractClassInfo() {
        extractSuperclass();
        extractInterfaces();
        fields = Stream.of(getOriginalClass().getDeclaredFields()).map(f -> Field.from(this, f, getClazzPool())).toArray(Field[]::new);
        methods = Stream.concat(Stream.of(getOriginalClass().getDeclaredMethods()).map(m -> new Method(m, getClazzPool())),
                Stream.of(getOriginalClass().getDeclaredConstructors()).map(m -> new Method(m, getClazzPool()))).toArray(Method[]::new);
    }

    public void extractSuperclass() {
        if(null != getOriginalClass().getSuperclass()) {
            superclass = getClazzPool().getFor(getOriginalClass().getSuperclass());
        }
    }

    public void extractInterfaces() {
        interfaces = Stream.of(getOriginalClass().getInterfaces()).map(i -> getClazzPool().getFor(i)).toArray(Clazz[]::new);
    }

    public Clazz[] getInterfaces() {
        return interfaces;
    }

    public boolean hasSuperclass() {
        return superclass != null;
    }

    public Clazz getSuperclass() {
        return superclass;
    }

    public Field[] getRelationshipFields() {
        return Stream.of(fields).filter(f -> f instanceof RelationshipField).toArray(Field[]::new);
    }

    public Field[] getNonRelationshipFields() {
        return Stream.of(fields).filter(f -> !(f instanceof RelationshipField)).toArray(Field[]::new);
    }

    public Method[] getConstructors() {
        return Stream.of(methods).filter(Method::isConstructor).toArray(Method[]::new);
    }

    public Method[] getNonConstructorMethods() {
        return Stream.of(methods).filter(m -> !m.isConstructor()).toArray(Method[]::new);
    }
}