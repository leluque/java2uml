package br.com.luque.java2uml.yuml.writer.classdiagram;

import br.com.luque.java2uml.core.classdiagram.reflection.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class YUMLConstructorWriterTest {
    /**
     * {@link YUMLConstructorWriter#getString(Method)}
     */
    @Test
    void givenDefaultConstructorWithOneParameter_whenGenerateText_thenProduceCorrectResult() {
        YUMLConstructorWriter yumlMethodWriter = new YUMLConstructorWriter();

        Method method = mock(Method.class);

        ScopedClazz parameterType = mock(ScopedClazz.class);
        when(parameterType.getName()).thenReturn("Object");

        Parameter parameter = mock(Parameter.class);
        when(parameter.getType()).thenReturn(parameterType);
        when(parameter.getName()).thenReturn("par1");
        when(method.getParameters()).thenReturn(new Parameter[]{parameter});
        when(method.getName()).thenReturn("name");
        when(method.getVisibility()).thenReturn(Visibilities.PACKAGE);

        String actualString = yumlMethodWriter.getString(method);
        assertEquals("~⪡create⪢name(par1:Object)", actualString);
    }

    /**
     * {@link YUMLConstructorWriter#getString(Method)}
     */
    @Test
    void givenDefaultConstructorWithNoParameters_whenGenerateText_thenProduceCorrectResult() {
        YUMLConstructorWriter yumlMethodWriter = new YUMLConstructorWriter();

        Method method = mock(Method.class);

        when(method.getParameters()).thenReturn(new Parameter[]{});
        when(method.getName()).thenReturn("name");
        when(method.getVisibility()).thenReturn(Visibilities.PACKAGE);

        String actualString = yumlMethodWriter.getString(method);
        assertEquals("~⪡create⪢name()", actualString);
    }

    /**
     * {@link YUMLConstructorWriter#getString(Method)}
     */
    @Test
    void givenDefaultConstructorWithTwoParameters_whenGenerateText_thenProduceCorrectResult() {
        YUMLConstructorWriter yumlMethodWriter = new YUMLConstructorWriter();

        Method method = mock(Method.class);

        ScopedClazz parameter1Type = mock(ScopedClazz.class);
        when(parameter1Type.getName()).thenReturn("Object");
        Parameter parameter1 = mock(Parameter.class);
        when(parameter1.getType()).thenReturn(parameter1Type);
        when(parameter1.getName()).thenReturn("par1");

        UnscopedClazz parameter2Type = mock(UnscopedClazz.class);
        when(parameter2Type.getName()).thenReturn("double");
        Parameter parameter2 = mock(Parameter.class);
        when(parameter2.getType()).thenReturn(parameter2Type);
        when(parameter2.getName()).thenReturn("par2");

        when(method.getParameters()).thenReturn(new Parameter[]{parameter1, parameter2});
        when(method.getName()).thenReturn("name");
        when(method.getVisibility()).thenReturn(Visibilities.PACKAGE);

        String actualString = yumlMethodWriter.getString(method);
        assertEquals("~⪡create⪢name(par1:Object,par2:double)", actualString);
    }

    /**
     * {@link YUMLConstructorWriter#getString(Method)}
     */
    @Test
    void givenPublicConstructorWithNoParameters_whenGenerateText_thenProduceCorrectResult() {
        YUMLConstructorWriter yumlMethodWriter = new YUMLConstructorWriter();

        Method method = mock(Method.class);
        when(method.getParameters()).thenReturn(new Parameter[]{});
        when(method.getName()).thenReturn("name");
        when(method.getVisibility()).thenReturn(Visibilities.PUBLIC);

        String actualString = yumlMethodWriter.getString(method);
        assertEquals("+⪡create⪢name()", actualString);
    }

    /**
     * {@link YUMLConstructorWriter#getString(Method)}
     */
    @Test
    void givenPrivateConstructorWithNoParameters_whenGenerateText_thenProduceCorrectResult() {
        YUMLConstructorWriter yumlMethodWriter = new YUMLConstructorWriter();

        Method method = mock(Method.class);
        when(method.getParameters()).thenReturn(new Parameter[]{});
        when(method.getName()).thenReturn("name");
        when(method.getVisibility()).thenReturn(Visibilities.PRIVATE);

        String actualString = yumlMethodWriter.getString(method);
        assertEquals("-⪡create⪢name()", actualString);
    }

    /**
     * {@link YUMLConstructorWriter#getString(Method)}
     */
    @Test
    void givenProtectedConstructorWithNoParameters_whenGenerateText_thenProduceCorrectResult() {
        YUMLConstructorWriter yumlMethodWriter = new YUMLConstructorWriter();

        Method method = mock(Method.class);
        when(method.getParameters()).thenReturn(new Parameter[]{});
        when(method.getName()).thenReturn("name");
        when(method.getVisibility()).thenReturn(Visibilities.PROTECTED);

        String actualString = yumlMethodWriter.getString(method);
        assertEquals("#⪡create⪢name()", actualString);
    }
}
