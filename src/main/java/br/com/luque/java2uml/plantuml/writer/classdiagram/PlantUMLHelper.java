package br.com.luque.java2uml.plantuml.writer.classdiagram;

import br.com.luque.java2uml.core.classdiagram.reflection.model.Field;
import br.com.luque.java2uml.core.classdiagram.reflection.model.Method;
import br.com.luque.java2uml.core.classdiagram.reflection.model.RelationshipField;
import br.com.luque.java2uml.core.classdiagram.reflection.model.Visibilities;

public class PlantUMLHelper {
    public static final String STATIC_TEXT = "{static}";
    public static final String ABSTRACT_TEXT = "{abstract}";

    public static String getVisibilityText(Visibilities visibility) {
        return switch (visibility) {
            case PUBLIC -> "+";
            case PROTECTED -> "#";
            case PRIVATE -> "-";
            default -> "~";
        };
    }

    public static String getStereotypeText(String stereotype) {
        return String.format("<<%s>>", stereotype);
    }

    public static String getStaticTextFor(Field field) {
        return field.isStatic() ? String.format(" %s ", STATIC_TEXT) : "";
    }

    public static String getStaticTextFor(Method method) {
        return method.isStatic() ? String.format(" %s ", STATIC_TEXT) : "";
    }

    public static String geAbstractTextFor(Method method) {
        return method.isAbstract() ? String.format(" %s ", ABSTRACT_TEXT) : "";
    }

    public static String getCardinalityTextFor(RelationshipField.Cardinalities cardinality) {
        return switch (cardinality) {
            case ONE -> " \"1\" ";
            case N -> " \"*\" ";
        };
    }
}
