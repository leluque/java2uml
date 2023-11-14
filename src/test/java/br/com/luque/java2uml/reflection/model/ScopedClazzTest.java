package br.com.luque.java2uml.reflection.model;

import br.com.luque.java2uml.ClazzPool;
import br.com.luque.java2uml.Rules;
import br.com.luque.java2uml.core.reflection.model.RelationshipField;
import br.com.luque.java2uml.core.reflection.model.ScopedClazz;
import br.com.luque.java2uml.reflection.model.data.ClassWithOnlyCommonJavaTypes;
import br.com.luque.java2uml.reflection.model.data.ClassWithOnlyRelationships;
import br.com.luque.java2uml.reflection.model.data.EmptyClass;
import br.com.luque.java2uml.reflection.model.data.MixedClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.AbstractList;
import java.util.stream.Stream;

public class ScopedClazzTest {
    @Test
    public void givenNullJavaClass_whenNewScopedClazz_thenThrowsException() {
        Assertions.assertThrows(NullPointerException.class, () -> new ScopedClazz(null, new ClazzPool(new Rules())));
    }

    @Test
    public void givenNullClazzPool_whenNewScopedClazz_thenThrowsException() {
        Assertions.assertThrows(NullPointerException.class, () -> new ScopedClazz(String.class, null));
    }

    @Test
    public void givenConcreteClass_whenNewScopedClazz_thenIsNotInterface() {
        ScopedClazz clazz = new ScopedClazz(String.class, new ClazzPool(new Rules()));
        Assertions.assertFalse(clazz.isInterface());
    }

    @Test
    public void givenConcreteClass_whenNewScopedClazz_thenIsNotAbstract() {
        ScopedClazz clazz = new ScopedClazz(String.class, new ClazzPool(new Rules()));
        Assertions.assertFalse(clazz.isAbstract());
    }

    @Test
    public void givenAbstractClass_whenNewScopedClazz_thenIsNotInterface() {
        ScopedClazz clazz = new ScopedClazz(AbstractList.class, new ClazzPool(new Rules()));
        Assertions.assertFalse(clazz.isInterface());
    }

    @Test
    public void givenAbstractClass_whenNewScopedClazz_thenIsAbstract() {
        ScopedClazz clazz = new ScopedClazz(AbstractList.class, new ClazzPool(new Rules()));
        Assertions.assertTrue(clazz.isAbstract());
    }

    @Test
    public void givenInterface_whenNewScopedClazz_thenIsInterface() {
        ScopedClazz clazz = new ScopedClazz(Runnable.class, new ClazzPool(new Rules()));
        Assertions.assertTrue(clazz.isInterface());
    }

    @Test
    public void givenInterface_whenNewScopedClazz_thenIsAbstract() {
        ScopedClazz clazz = new ScopedClazz(Runnable.class, new ClazzPool(new Rules()));
        Assertions.assertTrue(clazz.isAbstract());
    }

    @Test
    public void givenEmptyClass_whenNewScopedClazz_thenHasNoFields() {
        ScopedClazz clazz = new ScopedClazz(EmptyClass.class, new ClazzPool(new Rules()));
        clazz.extractClassInfo();
        Assertions.assertEquals(0, clazz.countFields());
    }

    @Test
    public void givenEmptyClass_whenNewScopedClazz_thenHasOnlyDefaultConstructor() {
        ScopedClazz clazz = new ScopedClazz(EmptyClass.class, new ClazzPool(new Rules()));
        clazz.extractClassInfo();
        Assertions.assertEquals(1, clazz.countMethods());
        Assertions.assertEquals(0, clazz.countNonConstructorMethods());
        Assertions.assertEquals(1, clazz.countConstructors());
    }

    @Test
    public void givenEmptyClass_whenNewScopedClazz_thenHasSuperclassObject() {
        ClazzPool clazzPool = new ClazzPool(new Rules().addClasses("java.lang.Object"));
        ScopedClazz clazz = new ScopedClazz(EmptyClass.class, clazzPool);
        clazz.extractClassInfo();
        Assertions.assertTrue(clazz.hasSuperclass());
        Assertions.assertEquals(clazz.getSuperclass(), clazzPool.getFor(Object.class));
    }

    @Test
    public void givenEmptyClass_whenNewScopedClazz_thenHasInterfaces() {
        ClazzPool clazzPool = new ClazzPool(new Rules());
        ScopedClazz clazz = new ScopedClazz(EmptyClass.class, clazzPool);
        clazz.extractClassInfo();
        Assertions.assertFalse(clazz.hasInterfaces());
        Assertions.assertEquals(0, clazz.countInterfaces());
    }

    @Test
    public void givenClassWithOnlyCommonJavaTypes_whenNewScopedClazz_thenHasFields() {
        ScopedClazz clazz = new ScopedClazz(ClassWithOnlyCommonJavaTypes.class, new ClazzPool(new Rules()));
        clazz.extractClassInfo();
        Assertions.assertEquals(21, clazz.countFields());
    }

    @Test
    public void givenClassWithOnlyCommonJavaTypes_whenNewScopedClazz_thenHasNoRelationshipFields() {
        ScopedClazz clazz = new ScopedClazz(ClassWithOnlyCommonJavaTypes.class, new ClazzPool(new Rules()));
        clazz.extractClassInfo();
        Assertions.assertEquals(0, clazz.countRelationshipFields());
    }

    @Test
    public void givenClassWithOnlyRelationships_whenNewScopedClazz_thenHasRelationshipFields() {
        ClazzPool clazzPool = new ClazzPool(new Rules().addPackages("br.com.luque.java2uml.reflection.model.data"));
        ScopedClazz clazz = new ScopedClazz(ClassWithOnlyRelationships.class, clazzPool);
        clazz.extractClassInfo();
        Assertions.assertEquals(13, clazz.countRelationshipFields());
    }

    @Test
    public void givenClassWithOnlyRelationships_whenNewScopedClazz_thenHas10RelationshipsWithCardinalityN() {
        ClazzPool clazzPool = new ClazzPool(new Rules().addPackages("br.com.luque.java2uml.reflection.model.data"));
        ScopedClazz clazz = new ScopedClazz(ClassWithOnlyRelationships.class, clazzPool);
        clazz.extractClassInfo();
        RelationshipField[] relationshipFields = clazz.getRelationshipFields();
        Assertions.assertEquals(10, Stream.of(relationshipFields).filter(f -> f.getCardinality() == RelationshipField.Cardinalities.N).count());
    }

    @Test
    public void givenClassWithOnlyRelationships_whenNewScopedClazz_thenHas3RelationshipsWithCardinality1() {
        ClazzPool clazzPool = new ClazzPool(new Rules().addPackages("br.com.luque.java2uml.reflection.model.data"));
        ScopedClazz clazz = new ScopedClazz(ClassWithOnlyRelationships.class, clazzPool);
        clazz.extractClassInfo();
        RelationshipField[] relationshipFields = clazz.getRelationshipFields();
        Assertions.assertEquals(3, Stream.of(relationshipFields).filter(f -> f.getCardinality() == RelationshipField.Cardinalities.ONE).count());
    }

    @Test
    public void givenClassWithOnlyRelationships_whenNewScopedClazz_thenHasRelationshipsWithCorrectTypes() {
        ClazzPool clazzPool = new ClazzPool(new Rules().addPackages("br.com.luque.java2uml.reflection.model.data"));
        ScopedClazz clazz = new ScopedClazz(ClassWithOnlyRelationships.class, clazzPool);
        clazz.extractClassInfo();
        Assertions.assertEquals(clazzPool.getFor(EmptyClass.class), clazz.getRelationshipField("field1").orElseThrow().getOtherSide());
        Assertions.assertEquals(clazzPool.getFor(ClassWithOnlyCommonJavaTypes.class), clazz.getRelationshipField("field2").orElseThrow().getOtherSide());
        Assertions.assertEquals(clazzPool.getFor(ClassWithOnlyCommonJavaTypes.class), clazz.getRelationshipField("field3").orElseThrow().getOtherSide());
        Assertions.assertEquals(clazzPool.getFor(ClassWithOnlyCommonJavaTypes.class), clazz.getRelationshipField("field4").orElseThrow().getOtherSide());
        Assertions.assertEquals(clazzPool.getFor(ClassWithOnlyCommonJavaTypes.class), clazz.getRelationshipField("field5").orElseThrow().getOtherSide());
        Assertions.assertEquals(clazzPool.getFor(ClassWithOnlyCommonJavaTypes.class), clazz.getRelationshipField("field6").orElseThrow().getOtherSide());
        Assertions.assertEquals(clazzPool.getFor(EmptyClass.class), clazz.getRelationshipField("field7").orElseThrow().getOtherSide());
        Assertions.assertEquals(clazzPool.getFor(EmptyClass.class), clazz.getRelationshipField("field8").orElseThrow().getOtherSide());
        Assertions.assertEquals(clazzPool.getFor(EmptyClass.class), clazz.getRelationshipField("field9").orElseThrow().getOtherSide());
        Assertions.assertEquals(clazzPool.getFor(EmptyClass.class), clazz.getRelationshipField("field10").orElseThrow().getOtherSide());
        Assertions.assertEquals(clazzPool.getFor(EmptyClass.class), clazz.getRelationshipField("field11").orElseThrow().getOtherSide());
        Assertions.assertEquals(clazzPool.getFor(EmptyClass.class), clazz.getRelationshipField("field12").orElseThrow().getOtherSide());
        Assertions.assertEquals(clazzPool.getFor(ClassWithOnlyRelationships.class), clazz.getRelationshipField("field13").orElseThrow().getOtherSide());
    }

    @Test
    public void givenMixedClass_whenNewScopedClazz_thenHasRelationshipFields() {
        ClazzPool clazzPool = new ClazzPool(new Rules().addPackages("br.com.luque.java2uml.reflection.model.data"));
        ScopedClazz clazz = new ScopedClazz(MixedClass.class, clazzPool);
        clazz.extractClassInfo();
        Assertions.assertEquals(13, clazz.countRelationshipFields());
    }

    @Test
    public void givenMixedClass_whenNewScopedClazz_thenHasNonRelationshipFields() {
        ClazzPool clazzPool = new ClazzPool(new Rules().addPackages("br.com.luque.java2uml.reflection.model.data"));
        ScopedClazz clazz = new ScopedClazz(MixedClass.class, clazzPool);
        clazz.extractClassInfo();
        Assertions.assertEquals(8, clazz.countNonRelationshipFields());
    }

    @Test
    public void givenMixedClass_whenNewScopedClazz_thenHas2Constructors() {
        ClazzPool clazzPool = new ClazzPool(new Rules().addPackages("br.com.luque.java2uml.reflection.model.data"));
        ScopedClazz clazz = new ScopedClazz(MixedClass.class, clazzPool);
        clazz.extractClassInfo();
        Assertions.assertEquals(2, clazz.countConstructors());
    }

    @Test
    public void givenMixedClass_whenNewScopedClazz_thenHas45NonConstructorMethods() {
        ClazzPool clazzPool = new ClazzPool(new Rules().addPackages("br.com.luque.java2uml.reflection.model.data"));
        ScopedClazz clazz = new ScopedClazz(MixedClass.class, clazzPool);
        clazz.extractClassInfo();
        Assertions.assertEquals(45, clazz.countNonConstructorMethods());
    }
}
