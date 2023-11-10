package br.com.luque.java2uml.yuml.writer;

import br.com.luque.java2uml.reflection.model.Clazz;
import br.com.luque.java2uml.reflection.model.RelationshipField;
import br.com.luque.java2uml.reflection.model.ScopedClazz;

import java.util.Objects;

public class YUMLRelationshipWriter implements br.com.luque.java2uml.writer.RelationshipWriter {
	public String getString(RelationshipField field) {
		Objects.requireNonNull(field);
		String result = "[";
		result += field.getClazz().getName();
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
		result += field.getOtherSide().getName();
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
			result += "[" + scopedClazz.getName() + "]^-.-[" + interface_.getName() + "]";
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
		result += "[" + scopedClazz.getName() + "]^[" + scopedClazz.getSuperclass().getName() + "]";
		result += "\n";
		return result;
	}
}
