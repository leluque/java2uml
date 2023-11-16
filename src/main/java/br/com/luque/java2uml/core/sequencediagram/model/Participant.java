package br.com.luque.java2uml.core.sequencediagram.model;

import java.util.Objects;

public class Participant {
    private String objectId;
    private String className;

    public Participant(String className, String objectId) {
        setClassName(className);
        setObjectId(objectId);
    }

    public String getObjectId() {
        return objectId;
    }

    private void setObjectId(String objectId) {
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
        return className + " (" + objectId + ")";
    }
}
