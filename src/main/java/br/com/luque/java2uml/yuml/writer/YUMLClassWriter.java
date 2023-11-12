package br.com.luque.java2uml.yuml.writer;

import br.com.luque.java2uml.reflection.model.Clazz;
import br.com.luque.java2uml.reflection.model.ScopedClazz;
import br.com.luque.java2uml.reflection.model.UnscopedClazz;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class YUMLClassWriter implements br.com.luque.java2uml.writer.ClassWriter {
	private final YUMLAttributeWriter attributeWriter;
	private final YUMLConstructorWriter constructorWriter;
	private final YUMLMethodWriter methodWriter;
	
	public YUMLClassWriter(YUMLAttributeWriter attributeWriter,
						   YUMLConstructorWriter constructorWriter,
						   YUMLMethodWriter methodWriter) {
		this.attributeWriter = Objects.requireNonNull(attributeWriter);
		this.constructorWriter = Objects.requireNonNull(constructorWriter);
		this.methodWriter = Objects.requireNonNull(methodWriter);
	}

	@Override
	public String getString(Clazz clazz) {
		if(clazz instanceof UnscopedClazz) {
			return "";
		}

		String result = "[";
		result += getFormattedClassName(clazz);

		if(clazz instanceof ScopedClazz scopedClazz) {
			result += "|";
			result += Stream.of(scopedClazz.getNonRelationshipFields()).map(attributeWriter::getString).collect(Collectors.joining(";"));
			result += "|";
			if(!scopedClazz.isInterface() && scopedClazz.hasConstructors()) {
				result += Stream.of(scopedClazz.getConstructors()).map(constructorWriter::getString).collect(Collectors.joining(";"));
				result += ";";
			}
			result += Stream.of(scopedClazz.getNonConstructorMethods()).map(methodWriter::getString).collect(Collectors.joining(";"));

			if (result.charAt(result.length() - 1) == ';') {
				result = result.substring(0, result.length() - 1);
			}
		}
		result += "]";
		return result;
	}

	public static String getFormattedClassName(Clazz clazz) {
		String result = "";
		if(clazz.isInterface()) {
			result += "<<interface>>;";
		}

		if(clazz.isAbstract()) {
			result += "_" + clazz.getName() + "_";
		} else {
			result += clazz.getName();
		}
		return result;
	}
}
