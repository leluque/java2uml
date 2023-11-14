package br.com.luque.java2uml.reflection.model;

import br.com.luque.java2uml.ClazzPool;
import br.com.luque.java2uml.Rules;
import br.com.luque.java2uml.core.reflection.model.UnscopedClazz;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.AbstractList;

public class UnscopedClazzTest {
    @Test
    public void givenNullJavaClass_whenNewUnscopedClazz_thenThrowsException() {
        Assertions.assertThrows(NullPointerException.class, () -> new UnscopedClazz(null, new ClazzPool(new Rules())));
    }

    @Test
    public void givenNullClazzPool_whenNewUnscopedClazz_thenThrowsException() {
        Assertions.assertThrows(NullPointerException.class, () -> new UnscopedClazz(String.class, null));
    }

    @Test
    public void givenConcreteClass_whenNewUnscopedClazz_thenIsNotInterface() {
        UnscopedClazz clazz = new UnscopedClazz(String.class, new ClazzPool(new Rules()));
        Assertions.assertFalse(clazz.isInterface());
    }

    @Test
    public void givenConcreteClass_whenNewUnscopedClazz_thenIsNotAbstract() {
        UnscopedClazz clazz = new UnscopedClazz(String.class, new ClazzPool(new Rules()));
        Assertions.assertFalse(clazz.isAbstract());
    }

    @Test
    public void givenAbstractClass_whenNewUnscopedClazz_thenIsNotInterface() {
        UnscopedClazz clazz = new UnscopedClazz(AbstractList.class, new ClazzPool(new Rules()));
        Assertions.assertFalse(clazz.isInterface());
    }

    @Test
    public void givenAbstractClass_whenNewUnscopedClazz_thenIsAbstract() {
        UnscopedClazz clazz = new UnscopedClazz(AbstractList.class, new ClazzPool(new Rules()));
        Assertions.assertTrue(clazz.isAbstract());
    }

    @Test
    public void givenInterface_whenNewUnscopedClazz_thenIsInterface() {
        UnscopedClazz clazz = new UnscopedClazz(Runnable.class, new ClazzPool(new Rules()));
        Assertions.assertTrue(clazz.isInterface());
    }

    @Test
    public void givenInterface_whenNewUnscopedClazz_thenIsAbstract() {
        UnscopedClazz clazz = new UnscopedClazz(Runnable.class, new ClazzPool(new Rules()));
        Assertions.assertTrue(clazz.isAbstract());
    }
}
