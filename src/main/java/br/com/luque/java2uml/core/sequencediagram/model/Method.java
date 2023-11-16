package br.com.luque.java2uml.core.sequencediagram.model;

import java.util.Objects;

public class Method {
    private String className;
    private String name;
    private String returnType;
    private String[] parameterTypes;
    private boolean constructor;

    public Method(String className, boolean constructor, String name, String returnType, String... parameterTypes) {
        setClassName(className);
        setConstructor(constructor);
        setName(name);
        setReturnType(returnType);
        setParameterTypes(parameterTypes);
    }

    public boolean isConstructor() {
        return constructor;
    }

    private void setConstructor(boolean constructor) {
        this.constructor = constructor;
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

    public String getReturnType() {
        return returnType;
    }

    public boolean hasReturnType() {
        return returnType != null && !returnType.isEmpty();
    }

    private void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String[] getParameterTypes() {
        return parameterTypes;
    }

    private void setParameterTypes(String[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (hasReturnType()) {
            sb.append(returnType).append(" ");
        }
        sb.append(name).append("(");
        if (parameterTypes != null) {
            for (int i = 0; i < parameterTypes.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(parameterTypes[i]);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
