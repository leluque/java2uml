package br.com.luque.java2uml.reflection.model;

import br.com.luque.java2uml.ClazzPool;

public class UnscopedClazz extends Clazz {
    public UnscopedClazz(Class<?> javaClass, ClazzPool clazzPool) {
        super(javaClass, clazzPool);
    }
}
