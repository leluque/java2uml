package br.com.luque.java2uml.yuml.writer.classdiagram;

import br.com.luque.java2uml.core.classdiagram.reflection.model.Visibilities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class YUMLHelperTest {
    /**
     * {@link YUMLHelper#getVisibilityText(Visibilities)}
     */
    @Test
    void givenVisibility_whenGetYUMLRepresentation_thenProduceCorrectResult() {
        assertEquals("~", YUMLHelper.getVisibilityText(Visibilities.PACKAGE));
        assertEquals("+", YUMLHelper.getVisibilityText(Visibilities.PUBLIC));
        assertEquals("#", YUMLHelper.getVisibilityText(Visibilities.PROTECTED));
        assertEquals("-", YUMLHelper.getVisibilityText(Visibilities.PRIVATE));
    }

    /**
     * {@link YUMLHelper#getStereotypeText(String)}
     */
    @Test
    void givenStereotype_whenGetYUMLRepresentation_thenProduceCorrectResult() {
        assertEquals("⪡Stereotype⪢", YUMLHelper.getStereotypeText("Stereotype"));
    }
}
