package br.com.luque.java2uml.reflection.model;

import br.com.luque.java2uml.ClazzPool;

public class UnscopedClazz extends Clazz {
    public UnscopedClazz(Class<?> class_, ClazzPool clazzPool) {
        super(class_, clazzPool);
    }

    public static UnscopedClazz newInterface(Class<?> originalClass, ClazzPool clazzPool) {
        UnscopedClazz clazz = new UnscopedClazz(originalClass, clazzPool);
        clazz.setInterface(true);
        return clazz;
    }
}
