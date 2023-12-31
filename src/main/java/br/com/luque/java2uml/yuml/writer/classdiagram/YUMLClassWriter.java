package br.com.luque.java2uml.yuml.writer.classdiagram;

import br.com.luque.java2uml.core.classdiagram.reflection.model.Clazz;
import br.com.luque.java2uml.core.classdiagram.reflection.model.Method;
import br.com.luque.java2uml.core.classdiagram.reflection.model.ScopedClazz;
import br.com.luque.java2uml.core.classdiagram.reflection.model.UnscopedClazz;
import br.com.luque.java2uml.core.classdiagram.writer.ClassWriter;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class YUMLClassWriter implements ClassWriter {
    private final YUMLFieldWriter attributeWriter;
    private final YUMLConstructorWriter constructorWriter;
    private final YUMLMethodWriter methodWriter;
    private boolean generateAccessors = false;

    public YUMLClassWriter(YUMLFieldWriter attributeWriter,
                           YUMLConstructorWriter constructorWriter,
                           YUMLMethodWriter methodWriter) {
        this.attributeWriter = Objects.requireNonNull(attributeWriter);
        this.constructorWriter = Objects.requireNonNull(constructorWriter);
        this.methodWriter = Objects.requireNonNull(methodWriter);
    }

    public YUMLClassWriter generateAccessors(boolean generateAccessors) {
        this.generateAccessors = generateAccessors;
        return this;
    }

    @Override
    public String getString(Clazz clazz) {
        if (clazz instanceof UnscopedClazz) {
            return "";
        }

        StringBuilder result = new StringBuilder("[");
        result.append(getFormattedClassName(clazz));

        if (clazz instanceof ScopedClazz scopedClazz) {
            result.append("|");
            result.append(Stream.of(scopedClazz.getNonRelationshipFields()).map(attributeWriter::getString).collect(Collectors.joining(";")));
            result.append("|");
            if (!scopedClazz.isInterface() && scopedClazz.hasConstructors()) {
                result.append(Stream.of(scopedClazz.getConstructors()).map(constructorWriter::getString).collect(Collectors.joining(";")));
                result.append(";");
            }
            result.append(Stream.of(scopedClazz.getNonConstructorMethods()).filter(this::handleAccessorsFilter).map(methodWriter::getString).collect(Collectors.joining(";")));

            if (result.charAt(result.length() - 1) == ';') {
                result.deleteCharAt(result.length() - 1);
            }
        }
        result.append("]");
        return result.toString();
    }

    private static final String[] accessorsPrefix = {"set", "get", "is", "add"};

    private boolean handleAccessorsFilter(Method m) {
        return generateAccessors || (!generateAccessors && !Stream.of(accessorsPrefix).anyMatch(m.getName()::startsWith));
    }

    public static String getFormattedClassName(Clazz clazz) {
        StringBuilder result = new StringBuilder();
        if (clazz.isInterface()) {
            result.append("<<interface>>;");
        }

        if (clazz.isAbstract()) {
            result.append("_");
            result.append(clazz.getName());
            result.append("_");
        } else {
            result.append(clazz.getName());
        }
        return result.toString();
    }

}
