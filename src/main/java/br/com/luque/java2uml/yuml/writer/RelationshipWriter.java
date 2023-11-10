package br.com.luque.java2uml.yuml.writer;

import br.com.luque.java2uml.reflection.RelationshipField;
public class RelationshipWriter {
	public String getString(RelationshipField field) {
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
}
