package br.com.luque.java2uml.core.sequencediagram.model;

import br.com.luque.java2uml.core.sequencediagram.reflection.annotation.Stereotypes;

import java.util.Objects;

public class Participant {
    private Stereotypes stereotype;
    private String objectId;
    private String className;

    public Participant(String className, String objectId) {
        this(null, className, objectId);
    }

    public Participant(Stereotypes stereotype, String className, String objectId) {
        setStereotype(stereotype);
        setClassName(className);
        setObjectId(objectId);
    }

    public Stereotypes getStereotype() {
        return stereotype;
    }

    public void setStereotype(Stereotypes stereotype) {
        this.stereotype = stereotype;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getClassName() {
        return className;
    }

    private void setClassName(String className) {
        Objects.requireNonNull(className);
        className = className.trim();
        if (className.isEmpty()) {
            throw new IllegalArgumentException("Cannot be empty!");
        }
        this.className = className;
    }

    @Override
    public String toString() {
        return (objectId == null ? "" : objectId) + ":" + className;
    }
}
