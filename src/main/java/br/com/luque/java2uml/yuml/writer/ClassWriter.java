package br.com.luque.java2uml.yuml.writer;

import br.com.luque.java2uml.reflection.Clazz;
import br.com.luque.java2uml.reflection.ScopedClazz;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassWriter {
	private final AttributeWriter attributeWriter;
	private final ConstructorWriter constructorWriter;
	private final MethodWriter methodWriter;
	
	public ClassWriter(AttributeWriter attributeWriter,
					   ConstructorWriter constructorWriter,
					   MethodWriter methodWriter) {
		this.attributeWriter = Objects.requireNonNull(attributeWriter);
		this.constructorWriter = Objects.requireNonNull(constructorWriter);
		this.methodWriter = Objects.requireNonNull(methodWriter);
	}

	public String getString(Clazz clazz) {
		String result = "[";

		if(clazz.isInterface()) {
			result += "<<interface>>;";
		}

		if(clazz.isAbstract()) {
			result += "_" + clazz.getName() + "_";
		} else {
			result += clazz.getName();
		}

		if(clazz instanceof ScopedClazz scopedClazz) {
			result += "|";
			result += Stream.of(scopedClazz.getNonRelationshipFields()).map(attributeWriter::getString).collect(Collectors.joining(";"));
			result += "|";
			result += Stream.of(scopedClazz.getConstructors()).map(constructorWriter::getString).collect(Collectors.joining(";"));
			result += ";";
			result += Stream.of(scopedClazz.getNonConstructorMethods()).map(methodWriter::getString).collect(Collectors.joining(";"));

			if (result.charAt(result.length() - 1) == ';') {
				result = result.substring(0, result.length() - 1);
			}
		}
		result += "]";
		return result;
	}
}
