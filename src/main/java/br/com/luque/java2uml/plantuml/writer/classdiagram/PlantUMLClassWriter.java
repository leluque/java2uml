package br.com.luque.java2uml.plantuml.writer.classdiagram;

import br.com.luque.java2uml.core.classdiagram.reflection.model.Clazz;
import br.com.luque.java2uml.core.classdiagram.reflection.model.Method;
import br.com.luque.java2uml.core.classdiagram.reflection.model.ScopedClazz;
import br.com.luque.java2uml.core.classdiagram.reflection.model.UnscopedClazz;
import br.com.luque.java2uml.core.classdiagram.writer.ClassWriter;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlantUMLClassWriter implements ClassWriter {
    private final PlantUMLFieldWriter fieldWriter;
    private final PlantUMLConstructorWriter constructorWriter;
    private final PlantUMLMethodWriter methodWriter;
    private boolean generateAccessors = false;

    public PlantUMLClassWriter(PlantUMLFieldWriter fieldWriter,
                               PlantUMLConstructorWriter constructorWriter,
                               PlantUMLMethodWriter methodWriter) {
        this.fieldWriter = Objects.requireNonNull(fieldWriter);
        this.constructorWriter = Objects.requireNonNull(constructorWriter);
        this.methodWriter = Objects.requireNonNull(methodWriter);
    }

    public PlantUMLClassWriter generateAccessors(boolean generateAccessors) {
        this.generateAccessors = generateAccessors;
        return this;
    }

    @Override
    public String getString(Clazz clazz) {
        if (clazz instanceof UnscopedClazz) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        result.append(getClassDefinition(clazz));
        result.append(" {\n");

        if (clazz instanceof ScopedClazz scopedClazz) {
            result.append(Stream.of(scopedClazz.getNonRelationshipFields()).map(f -> String.format("    %s", fieldWriter.getString(f))).collect(Collectors.joining("\n")));
            if (scopedClazz.countNonRelationshipFields() > 0) {
                result.append("\n");
            }
            if (!scopedClazz.isInterface() && scopedClazz.hasConstructors()) {
                result.append(Stream.of(scopedClazz.getConstructors()).map(c -> String.format("    %s", constructorWriter.getString(c))).collect(Collectors.joining("\n")));
                result.append("\n");
            }
            result.append(Stream.of(scopedClazz.getNonConstructorMethods()).filter(this::handleAccessorsFilter).map(m -> String.format("    %s", methodWriter.getString(m))).collect(Collectors.joining("\n")));
        }
        result.append("\n}");
        return result.toString();
    }

    private static final String[] accessorsPrefix = {"set", "get", "is", "add"};

    private boolean handleAccessorsFilter(Method m) {
        return generateAccessors || (!generateAccessors && !Stream.of(accessorsPrefix).anyMatch(m.getName()::startsWith));
    }

    public static String getClassDefinition(Clazz clazz) {
        StringBuilder result = new StringBuilder();
        if (clazz.isInterface()) {
            return String.format("interface %s", clazz.getName());
        } else if (clazz.isAbstract()) {
            return String.format("abstract class %s", clazz.getName());
        }
        return String.format("class %s", clazz.getName());
    }

}
