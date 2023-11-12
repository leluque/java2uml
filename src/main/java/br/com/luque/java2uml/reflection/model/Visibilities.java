package br.com.luque.java2uml.reflection.model;

import java.lang.reflect.Modifier;

public enum Visibilities {
    PACKAGE, PRIVATE, PROTECTED, PUBLIC;

    public static Visibilities fromModifiers(int modifiers) {
        return switch (modifiers) {
            case Modifier.PRIVATE -> PRIVATE;
            case Modifier.PROTECTED -> PROTECTED;
            case Modifier.PUBLIC -> PUBLIC;
            default -> PACKAGE;
        };
    }
}
