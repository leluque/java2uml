package br.com.luque.java2uml.reflection;

import java.lang.reflect.Modifier;
import java.util.Objects;

public class Field extends BaseItem {
    private java.lang.reflect.Field field;
    private Clazz clazz;
    private Visibilities visibility;
    private Clazz type;
    private boolean ztatic;

    protected Field(Clazz clazz, java.lang.reflect.Field field, ClazzPool clazzPool) {
        super(field.getName(), clazzPool);
        setClazz(clazz);
        setField(field);
        extractFieldInfo();
    }

    public static Field from(Clazz clazz, java.lang.reflect.Field field, ClazzPool clazzPool) {
        if(RelationshipField.isRelationship(field, clazzPool)) {
            return new RelationshipField(clazz, field, clazzPool);
        }
        return new Field(clazz, field, clazzPool);
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = Objects.requireNonNull(clazz);
    }

    public Visibilities getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibilities visibility) {
        this.visibility = Objects.requireNonNull(visibility);
    }

    public Clazz getType() {
        return type;
    }

    public void setType(Clazz type) {
        this.type = Objects.requireNonNull(type);
    }

    public boolean isStatic() {
        return ztatic;
    }

    public void setStatic(boolean ztatic) {
        this.ztatic = ztatic;
    }

    public java.lang.reflect.Field getField() {
        return field;
    }

    private void setField(java.lang.reflect.Field field) {
        this.field = Objects.requireNonNull(field);
    }

    private void extractFieldInfo() {
        setVisibility(Visibilities.fromModifiers(field.getModifiers()));
        setType(getClazzPool().getFor(field.getType()));
        setStatic(Modifier.isStatic(field.getModifiers()));
    }
}
