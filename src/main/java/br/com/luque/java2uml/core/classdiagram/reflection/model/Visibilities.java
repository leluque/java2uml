package br.com.luque.java2uml.core.classdiagram.reflection.model;

import java.lang.reflect.Modifier;

public enum Visibilities {
    PACKAGE, PRIVATE, PROTECTED, PUBLIC;

    public static Visibilities fromModifiers(int modifiers) {
        if(Modifier.isPrivate(modifiers)) {
            return PRIVATE;
        } else if(Modifier.isProtected(modifiers)) {
            return PROTECTED;
        } else if(Modifier.isPublic(modifiers)) {
            return PUBLIC;
        } else {
            return PACKAGE;
        }
    }
}
