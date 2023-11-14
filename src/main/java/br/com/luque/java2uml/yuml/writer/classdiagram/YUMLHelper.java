package br.com.luque.java2uml.yuml.writer.classdiagram;

import br.com.luque.java2uml.core.reflection.model.Visibilities;

public class YUMLHelper {
    public static String getVisibilityText(Visibilities visibility) {
        return switch (visibility) {
            case PUBLIC -> "+";
            case PROTECTED -> "#";
            case PRIVATE -> "-";
            default -> "~";
        };
    }

    public static String getStereotypeText(String stereotype) {
        return String.format("⪡%s⪢", stereotype);
    }
}
