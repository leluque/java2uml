package br.com.luque.java2uml.plantuml.writer.classdiagram;

import br.com.luque.java2uml.core.classdiagram.reflection.model.Method;
import br.com.luque.java2uml.core.classdiagram.writer.MethodWriter;
import br.com.luque.java2uml.plantuml.PlantUML;
import br.com.luque.java2uml.yuml.writer.classdiagram.YUMLHelper;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlantUMLMethodWriter implements MethodWriter {
    public String getString(Method method) {
        Objects.requireNonNull(method);

        return PlantUMLHelper
            .getVisibilityText(method.getVisibility()) + PlantUMLHelper.getStaticTextFor(method) + PlantUMLHelper.geAbstractTextFor(method) + method.getName() + "(" + Stream.of(method.getParameters()).map(p -> String.format("%s:%s", p.getName(), p.getType().getName())).collect(Collectors.joining(",")) + "):" + (method.getReturnType() == null ? "void" : method.getReturnType().getName());
    }
}
