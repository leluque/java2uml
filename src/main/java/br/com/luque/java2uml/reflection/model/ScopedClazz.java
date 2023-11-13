package br.com.luque.java2uml.reflection.model;

import br.com.luque.java2uml.ClazzPool;

import java.util.Optional;
import java.util.stream.Stream;

public class ScopedClazz extends Clazz {
    private Clazz superclass;
    private Clazz[] interfaces;
    private Field[] fields;
    private Method[] methods;

    public ScopedClazz(Class<?> javaClass, ClazzPool clazzPool) {
        super(javaClass, clazzPool);
    }

    public void extractClassInfo() {
        extractSuperclass();
        extractInterfaces();
        fields = Stream.of(getJavaClass().getDeclaredFields()).map(f -> Field.from(this, f, getClazzPool())).toArray(Field[]::new);
        methods = Stream.concat(Stream.of(getJavaClass().getDeclaredMethods()).map(m -> new Method(m, getClazzPool())),
            Stream.of(getJavaClass().getDeclaredConstructors()).map(m -> new Method(m, getClazzPool()))).toArray(Method[]::new);
    }

    public void extractSuperclass() {
        if (null != getJavaClass().getSuperclass() && getClazzPool().getRules().includes(getJavaClass().getSuperclass())) {
            superclass = getClazzPool().getFor(getJavaClass().getSuperclass());
        }
    }

    public int countSuperclasses() {
        return hasSuperclass() ? 1 : 0;
    }

    public boolean hasSuperclass() {
        return superclass != null;
    }

    public Clazz getSuperclass() {
        return superclass;
    }


    public int countInterfaces() {
        return interfaces.length;
    }

    public void extractInterfaces() {
        interfaces = Stream.of(getJavaClass().getInterfaces()).filter(i -> getClazzPool().getRules().includes(i)).map(i -> getClazzPool().getFor(i)).toArray(Clazz[]::new);
    }

    public boolean hasInterfaces() {
        return getInterfaces() != null && getInterfaces().length > 0;
    }

    public Clazz[] getInterfaces() {
        return interfaces;
    }

    public int countFields() {
        return fields.length;
    }

    public int countMethods() {
        return methods.length;
    }

    public int countConstructors() {
        return Stream.of(methods).filter(Method::isConstructor).toArray().length;
    }

    public int countNonConstructorMethods() {
        return Stream.of(methods).filter(m -> !m.isConstructor()).toArray().length;
    }

    public int countRelationshipFields() {
        return Stream.of(fields).filter(f -> f instanceof RelationshipField).toArray().length;
    }

    public int countNonRelationshipFields() {
        return Stream.of(fields).filter(f -> !(f instanceof RelationshipField)).toArray().length;
    }

    public RelationshipField[] getRelationshipFields() {
        return Stream.of(fields).filter(f -> f instanceof RelationshipField).toArray(RelationshipField[]::new);
    }

    public Optional<RelationshipField> getRelationshipField(String fieldName) {
        if (null == fields) {
            return Optional.empty();
        }
        return Stream.of(fields).filter(f -> f instanceof RelationshipField).map(RelationshipField.class::cast).filter(f -> f.getName().equals(fieldName)).findFirst();
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

    public boolean hasConstructors() {
        return getNonConstructorMethods().length > 0;
    }
}
