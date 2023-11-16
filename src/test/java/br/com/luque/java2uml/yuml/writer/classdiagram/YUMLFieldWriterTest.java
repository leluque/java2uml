package br.com.luque.java2uml.yuml.writer.classdiagram;

import br.com.luque.java2uml.core.classdiagram.reflection.model.Field;
import br.com.luque.java2uml.core.classdiagram.reflection.model.RelationshipField;
import br.com.luque.java2uml.core.classdiagram.reflection.model.ScopedClazz;
import br.com.luque.java2uml.core.classdiagram.reflection.model.Visibilities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class YUMLFieldWriterTest {
    @Test
    void givenDefaultField_whenGenerateText_thenProduceCorrectResult() {
        YUMLFieldWriter yumlAttributeWriter = new YUMLFieldWriter();

        Field field = mock(Field.class);

        ScopedClazz fieldType = mock(ScopedClazz.class);
        when(fieldType.getName()).thenReturn("Object");

        when(field.getType()).thenReturn(fieldType);
        when(field.getName()).thenReturn("name");
        when(field.getVisibility()).thenReturn(Visibilities.PACKAGE);

        String actualString = yumlAttributeWriter.getString(field);
        assertEquals("~name:Object", actualString);
    }

    @Test
    void givenPrivateField_whenGenerateText_thenProduceCorrectResult() {
        YUMLFieldWriter yumlAttributeWriter = new YUMLFieldWriter();

        Field field = mock(Field.class);

        ScopedClazz fieldType = mock(ScopedClazz.class);
        when(fieldType.getName()).thenReturn("Object");

        when(field.getType()).thenReturn(fieldType);
        when(field.getName()).thenReturn("name");
        when(field.getVisibility()).thenReturn(Visibilities.PRIVATE);

        String actualString = yumlAttributeWriter.getString(field);
        assertEquals("-name:Object", actualString);
    }

    @Test
    void givenProtectedField_whenGenerateText_thenProduceCorrectResult() {
        YUMLFieldWriter yumlAttributeWriter = new YUMLFieldWriter();

        Field field = mock(Field.class);

        ScopedClazz fieldType = mock(ScopedClazz.class);
        when(fieldType.getName()).thenReturn("Object");

        when(field.getType()).thenReturn(fieldType);
        when(field.getName()).thenReturn("name");
        when(field.getVisibility()).thenReturn(Visibilities.PROTECTED);

        String actualString = yumlAttributeWriter.getString(field);
        assertEquals("#name:Object", actualString);
    }

    @Test
    void givenPublicField_whenGenerateText_thenProduceCorrectResult() {
        YUMLFieldWriter yumlAttributeWriter = new YUMLFieldWriter();

        Field field = mock(Field.class);

        ScopedClazz fieldType = mock(ScopedClazz.class);
        when(fieldType.getName()).thenReturn("Object");

        when(field.getType()).thenReturn(fieldType);
        when(field.getName()).thenReturn("name");
        when(field.getVisibility()).thenReturn(Visibilities.PUBLIC);

        String actualString = yumlAttributeWriter.getString(field);
        assertEquals("+name:Object", actualString);
    }

    @Test
    void givenRelationshipField_whenGenerateText_thenThrowException() {
        YUMLFieldWriter yumlAttributeWriter = new YUMLFieldWriter();

        RelationshipField field = mock(RelationshipField.class);

        ScopedClazz fieldType = mock(ScopedClazz.class);
        when(fieldType.getName()).thenReturn("Object");

        when(field.getType()).thenReturn(fieldType);
        when(field.getName()).thenReturn("name");
        when(field.getVisibility()).thenReturn(Visibilities.PACKAGE);

        assertThrows(IllegalArgumentException.class, () -> yumlAttributeWriter.getString(field));
    }
}
