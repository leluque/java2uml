package br.com.luque.java2uml.yuml.writer;

import br.com.luque.java2uml.reflection.model.Clazz;
import br.com.luque.java2uml.reflection.model.RelationshipField;
import br.com.luque.java2uml.reflection.model.ScopedClazz;

import java.util.Objects;

public class YUMLRelationshipWriter implements br.com.luque.java2uml.writer.RelationshipWriter {
	public String getString(RelationshipField field) {
		Objects.requireNonNull(field);
		String result = "[";
		result += YUMLClassWriter.getFormattedClassName(field.getClazz());
		result += "]";
		if(field.isAggregation()) {
			result += "<>";
		} else if(field.isComposition()) {
			result += "++";
		}
		result += switch(field.getCardinality()) {
			case ONE -> "->1";
			case N -> "->*";
		};

		result += "[";
		result += YUMLClassWriter.getFormattedClassName(field.getOtherSide());
		result += "]";
		return result;
	}

	public String getRealizationString(ScopedClazz scopedClazz) {
		Objects.requireNonNull(scopedClazz);
		if(!scopedClazz.hasInterfaces()) {
			return "";
		}
		String result = "";
		for(Clazz interface_ : scopedClazz.getInterfaces()) {
			result += "[" + YUMLClassWriter.getFormattedClassName(interface_) + "]^-.-[" + YUMLClassWriter.getFormattedClassName(scopedClazz) + "]";
			result += "\n";
		}
		return result;
	}

	public String getInheritanceString(ScopedClazz scopedClazz) {
		Objects.requireNonNull(scopedClazz);
		if(!scopedClazz.hasSuperclass()) {
			return "";
		}
		String result = "";
		result += "[" + YUMLClassWriter.getFormattedClassName(scopedClazz.getSuperclass()) + "]^[" + YUMLClassWriter.getFormattedClassName(scopedClazz) + "]";
		result += "\n";
		return result;
	}
}
