package br.com.luque.java2uml.yuml.writer.classdiagram;

import br.com.luque.java2uml.core.classdiagram.reflection.model.Method;
import br.com.luque.java2uml.core.classdiagram.writer.ConstructorWriter;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class YUMLConstructorWriter implements ConstructorWriter {
    public String getString(Method method) {
        Objects.requireNonNull(method);

        return YUMLHelper
            .getVisibilityText(method.getVisibility()) + YUMLHelper.getStereotypeText("create") + method.getName() + "(" + Stream.of(method.getParameters()).map(p -> String.format("%s:%s", p.getName(), p.getType().getName())).collect(Collectors.joining(",")) + ")";
    }
}
